/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.config;

import org.jgrades.config.api.model.UserData;
import org.jgrades.config.api.service.UserPreferencesService;
import org.jgrades.lic.api.aop.CheckLicence;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.api.config.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
@CheckLicence
@PreAuthorize("isAuthenticated()")
public class UserProfileService implements IUserProfileService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(UserProfileService.class);

    @Autowired
    private UserPreferencesService userPreferencesService;

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setProfileData(@RequestBody UserData userData) {
        LOGGER.trace("Setting new user data: {}", userData);
        userPreferencesService.setUserData(userData);
    }
}
