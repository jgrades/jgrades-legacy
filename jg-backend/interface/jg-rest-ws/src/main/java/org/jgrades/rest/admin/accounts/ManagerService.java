/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.admin.accounts;

import org.jgrades.admin.api.accounts.ManagerMgntService;
import org.jgrades.data.api.entities.Manager;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/manager", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class ManagerService extends AbstractUserService<Manager> {
    @Autowired
    protected ManagerService(ManagerMgntService userManagerService) {
        super(userManagerService);
    }
}
