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

import org.jgrades.data.api.dao.accounts.UserRepository;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.security.api.entities.PasswordPolicy;
import org.jgrades.security.api.service.PasswordMgntService;
import org.jgrades.security.api.service.PasswordPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/password")
@CheckSystemDependencies
public class PasswordsService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(PasswordsService.class);

    @Autowired
    private PasswordMgntService passwordMgntService;

    @Autowired
    private PasswordPolicyService passwordPolicyService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> setPassword(@RequestBody PasswordDTO passwordInfo) {
        LOGGER.trace("Setting password for user with id {}", passwordInfo.getUserId());
        passwordMgntService.setPassword(passwordInfo.getPassword(), userRepository.findOne(passwordInfo.getUserId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/policy/{role}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PasswordPolicy getForRole(@PathVariable JgRole role) {
        LOGGER.trace("Get password policy for role {}", role);
        return passwordPolicyService.getForRole(role);
    }

    @RequestMapping(value = "/policy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<PasswordPolicy> getAll() {
        LOGGER.trace("Getting password policies for all roles");
        return passwordPolicyService.getPasswordPolicies();
    }

    @RequestMapping(value = "/policy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> setForRole(@RequestBody PasswordPolicy policy) {
        LOGGER.trace("Setting new password policy: {}", policy);
        passwordPolicyService.putPasswordPolicy(policy);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
