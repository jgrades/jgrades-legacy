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

import org.jgrades.config.api.model.UserData;
import org.jgrades.data.api.entities.Subject;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.YearLevel;
import org.jgrades.data.api.entities.roles.ManagerDetails;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.rest.api.security.PasswordDTO;
import org.jgrades.rest.client.BaseTest;
import org.jgrades.rest.client.admin.general.SchoolServiceClient;
import org.jgrades.rest.client.admin.general.SubjectServiceClient;
import org.jgrades.rest.client.admin.structures.YearLevelServiceClient;
import org.jgrades.rest.client.config.UserProfileServiceClient;
import org.jgrades.rest.client.security.PasswordServiceClient;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumMap;
import java.util.List;

@Ignore
public class UserServiceClientTest extends BaseTest {
    @Autowired
    private PasswordServiceClient passwordServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private UserProfileServiceClient userProfileServiceClient;

    @Autowired
    private YearLevelServiceClient yearLevelServiceClient;

    @Autowired
    private SchoolServiceClient schoolServiceClient;

    @Autowired
    private SubjectServiceClient subjectServiceClient;


    @Test
    public void test2() throws Exception {
//        YearLevel yearLevel = new YearLevel();
//        yearLevel.setName("I gim");
//        yearLevelServiceClient.insertOrUpdate(yearLevel);

        YearLevel withId = yearLevelServiceClient.getWithId(1L);

    }

    @Test
    public void testschoo() throws Exception {
        List<Subject> page = subjectServiceClient.getAll();

    }

    @Test
    public void testName() throws Exception {
        User admin = userServiceClient.getWithId(5L);
        admin.setEmail("aa@AAAA.aa");
        UserData ud = new UserData();
        ud.setUser(admin);
        ud.setPassword(null);
        userProfileServiceClient.setProfileData(ud);

    }

    @Test
    public void createManager() throws Exception {
        // given
        User manager = new User();
        manager.setLogin("manager1");
        EnumMap<JgRole, RoleDetails> roles = new EnumMap<>(JgRole.class);
        roles.put(JgRole.MANAGER, new ManagerDetails());
        manager.setRoles(roles);
        manager.setName("Manager Test");

        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setLogin("manager1");
        passwordDTO.setPassword("manager123");

        // when
        userServiceClient.insertOrUpdate(manager);
        passwordServiceClient.setPassword(passwordDTO);

    }
}
