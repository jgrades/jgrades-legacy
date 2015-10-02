/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.security;

import org.jgrades.data.api.dao.accounts.GenericUserRepository;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.security.api.service.PasswordMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/password")
@CheckSystemDependencies
public class PasswordsService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(PasswordsService.class);

    @Autowired
    private PasswordMgntService passwordMgntService;

    @Autowired
    private GenericUserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> setPassword(@RequestBody PasswordDTO passwordInfo) {
        passwordMgntService.setPassword(passwordInfo.getPassword(), userRepository.findOne(passwordInfo.getUserId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/expired/{id}", method = RequestMethod.GET)
    public boolean isPasswordExpired(@PathVariable Long id) {
        return passwordMgntService.isPasswordExpired(userRepository.findOne(id));
    }
}
