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

import org.dozer.Mapper;
import org.jgrades.data.api.dao.accounts.*;
import org.jgrades.data.api.entities.*;
import org.jgrades.data.api.model.roles.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserModelEnrichment<U extends User> {
    @Autowired
    private Mapper mapper;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository parentRepository;

    //TODO refactoring
    public U enrichWithRoles(U user) {
        Roles roles = new Roles();

        Administrator administrator = administratorRepository.findOne(user.getId());
        if (administrator != null) {
            roles.addRole(JgRole.ADMINISTRATOR, mapper.map(administrator, AdministratorDetails.class));
        }

        Manager manager = managerRepository.findOne(user.getId());
        if (manager != null) {
            roles.addRole(JgRole.MANAGER, mapper.map(manager, ManagerDetails.class));
        }

        Teacher teacher = teacherRepository.findOne(user.getId());
        if (teacher != null) {
            roles.addRole(JgRole.TEACHER, mapper.map(teacher, TeacherDetails.class));
        }

        Student student = studentRepository.findOne(user.getId());
        if (student != null) {
            roles.addRole(JgRole.STUDENT, mapper.map(student, StudentDetails.class));
        }

        Parent parent = parentRepository.findOne(user.getId());
        if (parent != null) {
            roles.addRole(JgRole.PARENT, mapper.map(parent, ParentDetails.class));
        }

        user.setRoles(roles);
        return user;
    }
}
