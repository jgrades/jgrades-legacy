/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.accounts.mass;

import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.admin.api.model.LoginGenerationStrategy;
import org.jgrades.data.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.stripEnd;
import static org.apache.commons.lang3.StringUtils.substring;

@Component
public class DefaultLoginGenerationStrategy implements LoginGenerationStrategy {
    private static final String NUMBERS = "0123456789";

    @Value("${admin.student.name.substring.length}")
    private int substringNameLength;

    @Value("${admin.student.surname.substring.length}")
    private int substringSurnameLength;

    @Autowired
    private UserMgntService userMgntService;

    @Override
    public String generateLogin(User user) {
        String name = user.getName();
        String surname = user.getSurname();

        String subName = substring(name, 0, substringNameLength);
        String subSurname = substring(surname, 0, substringSurnameLength);

        return checkAvailability(subName + subSurname);
    }

    private String checkAvailability(String proposeLogin) {
        int counter = 1;
        return checkAvailabilityIfBusy(proposeLogin, counter);
    }

    private String checkAvailabilityIfBusy(String proposeLogin, int counter) {
        if (loginExists(proposeLogin)) {
            String newProposeLogin = stripEnd(proposeLogin, NUMBERS) + Integer.toString(counter);
            return checkAvailabilityIfBusy(newProposeLogin, ++counter);
        } else {
            return proposeLogin;
        }
    }

    private boolean loginExists(String newLogin) {
        return userMgntService.getWithLogin(newLogin) != null;
    }
}
