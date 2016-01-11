/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.config.service;

import org.jgrades.config.api.model.UserData;
import org.jgrades.config.api.service.UserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPreferencesServiceImpl implements UserPreferencesService {
    @Autowired
    private UserPreferencesUpdater userPreferencesUpdater;

    @Autowired
    private UserPasswordUpdater userPasswordUpdater;

    @Override
    public void setUserData(UserData userData) {
        userPreferencesUpdater.update(userData);
        userPasswordUpdater.update(userData);
    }
}
