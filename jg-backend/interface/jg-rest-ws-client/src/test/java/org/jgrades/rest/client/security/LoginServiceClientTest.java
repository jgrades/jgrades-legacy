/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.AdministratorDetails;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.rest.api.security.PasswordDTO;
import org.jgrades.rest.client.admin.accounts.UserServiceClient;
import org.jgrades.rest.client.context.RestClientContext;
import org.jgrades.rest.client.logging.LoggerConfigServiceClient;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.EnumMap;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestClientContext.class, ContainerEnvSimulatorConfig.class})
@WebAppConfiguration
@Ignore
public class LoginServiceClientTest {
    @Autowired
    private PasswordServiceClient passwordServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private LoginServiceClient loginServiceClient;

    @Autowired
    private LoggerConfigServiceClient loggerConfigServiceClient;


    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testName() throws Exception {
        User admin = new User();
        admin.setLogin("xyz");
        EnumMap<JgRole, RoleDetails> roles = new EnumMap<>(JgRole.class);
        roles.put(JgRole.ADMINISTRATOR, new AdministratorDetails());
        admin.setRoles(roles);

        userServiceClient.insertOrUpdate(admin);

        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setUserId(1L);
        passwordDTO.setPassword("omc2015");
        passwordServiceClient.setPassword(passwordDTO);

        loginServiceClient.logIn("xyz", "omc2015");
        LoggingConfiguration configuration = loggerConfigServiceClient.getConfiguration();
        System.out.println(configuration);
        configuration.setMaxDays(7);
        loggerConfigServiceClient.setNewConfiguration(configuration);
        System.out.println("NEW: " + loggerConfigServiceClient.getConfiguration());
        loginServiceClient.logOut();
        loggerConfigServiceClient.getConfiguration();
    }
}
