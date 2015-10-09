/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.accounts;

import com.google.common.collect.Maps;
import org.dozer.Mapper;
import org.jgrades.data.api.dao.accounts.*;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.model.JgRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public abstract class AbstractUserDetailsRepositories {
    @Autowired
    protected Mapper mapper;

    @Autowired
    protected AdministratorDetailsRepository administratorRepository;

    @Autowired
    protected ManagerDetailsRepository managerRepository;

    @Autowired
    protected TeacherDetailsRepository teacherRepository;

    @Autowired
    protected StudentDetailsRepository studentRepository;

    @Autowired
    protected ParentDetailsRepository parentRepository;

    protected Map<JgRole, CrudRepository<? extends RoleDetails, Long>> repos;

    @PostConstruct
    protected void fillMap() {
        repos = Maps.newEnumMap(JgRole.class);
        repos.put(JgRole.ADMINISTRATOR, administratorRepository);
        repos.put(JgRole.MANAGER, managerRepository);
        repos.put(JgRole.TEACHER, teacherRepository);
        repos.put(JgRole.STUDENT, studentRepository);
        repos.put(JgRole.PARENT, parentRepository);
    }
}
