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

import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.data.api.entities.User;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/loginuser")
@CheckSystemDependencies
public class LoginService {
    @Autowired
    private UserMgntService userMgntService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String name = auth.getName();
            if (name != null) {
                return userMgntService.getWithLogin(name);
            }
        }
        return null;
    }
}
