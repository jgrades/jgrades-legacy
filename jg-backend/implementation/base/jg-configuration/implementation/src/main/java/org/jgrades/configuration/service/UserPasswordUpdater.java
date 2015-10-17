/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.configuration.service;

import org.apache.commons.lang3.StringUtils;
import org.jgrades.configuration.api.model.UserData;
import org.jgrades.security.api.service.PasswordMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordUpdater implements UserUpdater {
    @Autowired
    private PasswordMgntService passwordMgntService;

    @Override
    public void update(UserData userData) {
        if (StringUtils.isNotEmpty(userData.getPassword())) {
            passwordMgntService.setPassword(userData.getPassword(), userData.getUser());
        }
    }
}
