/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.admin.accounts;

import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.ManagerDetails;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.rest.api.security.PasswordDTO;
import org.jgrades.rest.client.BaseTest;
import org.jgrades.rest.client.security.LoginServiceClient;
import org.jgrades.rest.client.security.PasswordServiceClient;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumMap;

@Ignore
public class UserServiceClientTest extends BaseTest {
    @Autowired
    private LoginServiceClient loginServiceClient;

    @Autowired
    private PasswordServiceClient passwordServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Test
    public void createManager() throws Exception {
        loginServiceClient.logIn("admin", "admin");

        User manager = new User();
        manager.setLogin("manager");
        EnumMap<JgRole, RoleDetails> roles = new EnumMap<>(JgRole.class);
        roles.put(JgRole.ADMINISTRATOR, new ManagerDetails());
        manager.setRoles(roles);

        userServiceClient.insertOrUpdate(manager);

        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setLogin("manager");
        passwordDTO.setPassword("manager123");
        passwordServiceClient.setPassword(passwordDTO);

    }
}
