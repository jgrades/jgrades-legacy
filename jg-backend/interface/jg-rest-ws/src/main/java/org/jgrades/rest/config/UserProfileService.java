/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.config;

import org.jgrades.config.api.model.UserData;
import org.jgrades.config.api.service.UserPreferencesService;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class UserProfileService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(UserProfileService.class);

    @Autowired
    private UserPreferencesService userPreferencesService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> setProfileData(@RequestBody UserData userData) {
        LOGGER.trace("Setting new user data: {}", userData);
        userPreferencesService.setUserData(userData);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
