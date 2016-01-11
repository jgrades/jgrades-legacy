/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.service;

import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.security.api.dao.PasswordDataRepository;
import org.jgrades.security.api.dao.PasswordPolicyRepository;
import org.jgrades.security.api.entities.PasswordData;
import org.jgrades.security.api.entities.PasswordPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

import static java.time.LocalDateTime.now;

@Component
public class LockingManager {
    @Value("${security.default.maximum.failed.authorization.amount}")
    private int defaultMaximumFailedLogins;

    @Value("${security.locking.account.minutes}")
    private int lockingMinutes;

    @Autowired
    private UserMgntService userMgntService;

    @Autowired
    private PasswordDataRepository passwordDataRepository;

    @Autowired
    private PasswordPolicyRepository passwordPolicyRepository;

    public boolean isLocked(User user) {
        PasswordData passwordData = passwordDataRepository.getPasswordDataWithUser(user.getLogin());
        if (passwordData != null && passwordData.getLockingDateTime() != null) {
            LocalDateTime endOfLock = passwordData.getLockingDateTime().plusMinutes(lockingMinutes);
            return endOfLock.isAfter(now());
        }
        return false;
    }

    public void setLockIfPossible(String login) {
        PasswordData passwordData = passwordDataRepository.getPasswordDataWithUser(login);
        if (passwordData != null && passwordData.getFailedLoginAmount() > 0) {
            User user = userMgntService.getWithLogin(login);
            Set<JgRole> userRoles = userMgntService.getUserRoles(user);
            JgRole highestRole = UserDetailsServiceImpl.getRoleWithHighestPriority(userRoles);
            PasswordPolicy passwordPolicy = passwordPolicyRepository.findOne(highestRole);
            Integer maximumNumberOfFailedLogin;
            if (passwordPolicy != null) {
                maximumNumberOfFailedLogin = passwordPolicy.getMaximumNumberOfFailedLogin();
            } else {
                maximumNumberOfFailedLogin = defaultMaximumFailedLogins;
            }
            if (maximumNumberOfFailedLogin.equals(passwordData.getFailedLoginAmount())) {
                passwordData.setLockingDateTime(now());
            }
        }
    }

    public void removeLockIfPossible(User user) {
        PasswordData passwordData = passwordDataRepository.getPasswordDataWithUser(user.getLogin());
        if (passwordData != null && passwordData.getFailedLoginAmount() > 0) {
            passwordData.setFailedLoginAmount(0);
            LocalDateTime lockingDateTime = passwordData.getLockingDateTime();
            if (lockingDateTime != null && lockingDateTime.plusMinutes(lockingMinutes).isBefore(now())) {
                passwordData.setLockingDateTime(null);
            }
        }
    }
}
