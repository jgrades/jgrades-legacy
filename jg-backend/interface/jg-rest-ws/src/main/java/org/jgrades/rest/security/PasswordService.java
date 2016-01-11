/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.security;

import org.jgrades.data.api.dao.accounts.UserRepository;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.api.security.IPasswordService;
import org.jgrades.rest.api.security.PasswordDTO;
import org.jgrades.security.api.entities.PasswordPolicy;
import org.jgrades.security.api.service.PasswordMgntService;
import org.jgrades.security.api.service.PasswordPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/password")
@CheckSystemDependencies
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class PasswordService implements IPasswordService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(PasswordService.class);

    @Autowired
    private PasswordMgntService passwordMgntService;

    @Autowired
    private PasswordPolicyService passwordPolicyService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setPassword(@RequestBody PasswordDTO passwordInfo) {
        LOGGER.trace("Setting password for user with login {}", passwordInfo.getLogin());
        passwordMgntService.setPassword(passwordInfo.getPassword(), userRepository.findFirstByLogin(passwordInfo.getLogin()));
    }

    @Override
    @RequestMapping(value = "/policy/{role}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PasswordPolicy getForRole(@PathVariable JgRole role) {
        LOGGER.trace("Get password policy for role {}", role);
        return passwordPolicyService.getForRole(role);
    }

    @Override
    @RequestMapping(value = "/policy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<PasswordPolicy> getAll() {
        LOGGER.trace("Getting password policies for all roles");
        return passwordPolicyService.getPasswordPolicies();
    }

    @Override
    @RequestMapping(value = "/policy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setForRole(@RequestBody PasswordPolicy policy) {
        LOGGER.trace("Setting new password policy: {}", policy);
        passwordPolicyService.putPasswordPolicy(policy);
    }
}
