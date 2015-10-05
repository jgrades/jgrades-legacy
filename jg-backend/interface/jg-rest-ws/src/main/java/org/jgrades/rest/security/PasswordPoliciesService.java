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

import org.jgrades.data.api.model.JgRole;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.security.api.entities.PasswordPolicy;
import org.jgrades.security.api.service.PasswordPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/password/policy")
@CheckSystemDependencies
public class PasswordPoliciesService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(PasswordPoliciesService.class);

    @Autowired
    private PasswordPolicyService passwordPolicyService;

    @RequestMapping(value = "/{role}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    PasswordPolicy getForRole(@PathVariable JgRole role) {
        return passwordPolicyService.getForRole(role);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Set<PasswordPolicy> getAll() {
        return passwordPolicyService.getPasswordPolicies();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> setForRole(@RequestBody PasswordPolicy policy) {
        passwordPolicyService.putPasswordPolicy(policy);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
