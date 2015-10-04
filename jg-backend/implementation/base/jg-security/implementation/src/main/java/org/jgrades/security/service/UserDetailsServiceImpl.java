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

import org.jgrades.data.api.dao.accounts.GenericUserRepository;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.roles.JgRole;
import org.jgrades.data.api.model.roles.Roles;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final static JgLogger LOGGER = JgLoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private GenericUserRepository userRepository;

    @Autowired
    private PasswordDataRepository passwordDataRepository;

    @Autowired
    private PasswordPolicyRepository passwordPolicyRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findFirstByLogin(login);
        if (user == null) {
            LOGGER.info(" Cannot find user with {} login", login);
            throw new UsernameNotFoundException("login: '" + login + "' not found");
        }
        return new UserDetailsImpl(
                login, getUserPassword(user), user.isActive(),
                true, isCredentialsNotExpired(user), true,
                null);
    }

    private boolean isCredentialsNotExpired(User user) {
        int expirationDaysForRole = getExpirationDays(getRoleWithHighestPriority(user.getRoles()));
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

    private JgRole getRoleWithHighestPriority(Roles roles) {
        return roles.getRoleMap().keySet().iterator().next();
    }

    private String getUserPassword(User user) {
        PasswordData passwordData = passwordDataRepository.getPasswordDataWithUser(user.getLogin());
        return passwordData.getPassword();
    }


}
