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
import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.admin.api.accounts.UserSpecifications;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.AdministratorDetails;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.security.api.service.PasswordMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

@Component
public class AdminCreator implements SmartLifecycle {
    @Value("${security.default.admin.login}")
    private String defaultAdminLogin;

    @Value("${security.default.admin.password}")
    private String defaultAdminPass;

    @Autowired
    private UserMgntService userMgntService;

    @Autowired
    private UserSpecifications userSpecifications;

    @Autowired
    private PasswordMgntService passwordMgntService;

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable runnable) {
        // not needed...
    }

    @Override
    public void start() {
        if (thereIsNotAnyAdmin()) {
            createAdmin();
        }
    }

    private boolean thereIsNotAnyAdmin() {
        Specification searchSpec = userSpecifications.withRoles(Sets.newHashSet(JgRole.ADMINISTRATOR));
        return userMgntService.get(searchSpec).isEmpty();
    }

    private void createAdmin() {
        User admin = new User();
        admin.setLogin(defaultAdminLogin);

        EnumMap<JgRole, RoleDetails> roles = new EnumMap<>(JgRole.class);
        roles.put(JgRole.ADMINISTRATOR, new AdministratorDetails());
        admin.setRoles(roles);

        userMgntService.saveOrUpdate(admin);

        passwordMgntService.setPassword(defaultAdminPass, admin);
    }

    @Override
    public void stop() {
        // not needed...
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
