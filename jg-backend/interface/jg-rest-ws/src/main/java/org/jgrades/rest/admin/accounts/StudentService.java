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

import org.jgrades.admin.api.accounts.MassAccountCreatorService;
import org.jgrades.admin.api.accounts.StudentMgntService;
import org.jgrades.data.api.entities.Student;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/student", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class StudentService extends AbstractUserService<Student> {
    @Autowired(required = false)//TODO
    private MassAccountCreatorService massAccountCreatorService;

    @Autowired
    protected StudentService(StudentMgntService userManagerService) {
        super(userManagerService);
    }
}
