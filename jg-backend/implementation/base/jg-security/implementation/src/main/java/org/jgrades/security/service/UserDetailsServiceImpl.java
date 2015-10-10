/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.service;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.data.api.dao.accounts.UserRepository;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.security.api.dao.PasswordDataRepository;
import org.jgrades.security.api.dao.PasswordPolicyRepository;
import org.jgrades.security.api.entities.PasswordData;
import org.jgrades.security.api.entities.PasswordPolicy;
import org.jgrades.security.utils.UserDetailsImpl;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMgntService userMgntService;

    @Autowired
    private PasswordDataRepository passwordDataRepository;

    @Autowired
    private PasswordPolicyRepository passwordPolicyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static JgRole getRoleWithHighestPriority(Set<JgRole> roles) {
        JgRole highestRole = null;
        for (JgRole role : roles) {
            if (highestRole == null) {
                highestRole = role;
                continue;
            }
            if (role.getPriority() > highestRole.getPriority()) {
                highestRole = role;
            }
        }
        return highestRole;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userRepository.findFirstByLogin(login);
        if (user == null) {
            LOGGER.info("Cannot find user with {} login", login);
            throw new UsernameNotFoundException("login: '" + login + "' not found");
        }
        return new UserDetailsImpl(
                login, getUserPassword(user), user.isActive(),
                true, isCredentialsNotExpired(user), true,
                getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = Sets.newHashSet();
        Set<JgRole> userRoles = userMgntService.getUserRoles(user);
        for (JgRole userRole : userRoles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.withRolePrefix());
            authorities.add(authority);
        }
        return authorities;
    }

    private boolean isCredentialsNotExpired(User user) {
        Set<JgRole> userRoles = userMgntService.getUserRoles(user);
        int expirationDaysForRole = getExpirationDays(getRoleWithHighestPriority(userRoles));
        if (expirationDaysForRole != 0) {
            DateTime lastPasswordChangeTime = passwordDataRepository.getPasswordDataWithUser(user.getLogin()).getLastChange();
            Duration duration = new Duration(lastPasswordChangeTime, DateTime.now());
            return duration.isShorterThan(Duration.standardDays(expirationDaysForRole));
        } else {
            return true;
        }
    }

    private int getExpirationDays(JgRole roleWithShortestPasswordPolicy) {
        PasswordPolicy passwordPolicy = passwordPolicyRepository.findOne(roleWithShortestPasswordPolicy);
        return passwordPolicy == null ? 0 : passwordPolicy.getExpirationDays();
    }

    private String getUserPassword(User user) {
        PasswordData passwordData = passwordDataRepository.getPasswordDataWithUser(user.getLogin());
        return passwordData == null ? StringUtils.EMPTY : passwordData.getPassword();
    }


}
