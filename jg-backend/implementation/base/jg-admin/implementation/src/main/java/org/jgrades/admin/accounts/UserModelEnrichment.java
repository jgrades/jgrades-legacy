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
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.data.api.utils.RolesMapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserModelEnrichment {
    @Autowired
    private Mapper mapper;

    @Autowired
    private AdministratorDetailsRepository administratorRepository;

    @Autowired
    private ManagerDetailsRepository managerRepository;

    @Autowired
    private TeacherDetailsRepository teacherRepository;

    @Autowired
    private StudentDetailsRepository studentRepository;

    @Autowired
    private ParentDetailsRepository parentRepository;

    private Map<JgRole, CrudRepository<? extends RoleDetails, Long>> map;

    @PostConstruct
    private void fillMap() {
        map = Maps.newEnumMap(JgRole.class);
        map.put(JgRole.ADMINISTRATOR, administratorRepository);
        map.put(JgRole.MANAGER, managerRepository);
        map.put(JgRole.TEACHER, teacherRepository);
        map.put(JgRole.STUDENT, studentRepository);
        map.put(JgRole.PARENT, parentRepository);
    }

    public User enrichWithRoles(User user) {
        if (user == null) {
            return null;
        }
        RolesMapBuilder rolesMapBuilder = new RolesMapBuilder();

        for (JgRole role : map.keySet()) {
            CrudRepository<? extends RoleDetails, Long> repo = map.get(role);
            RoleDetails roleDetails = repo.findOne(user.getId());
            if (roleDetails != null) {
                rolesMapBuilder.addRole(role, roleDetails);
            }
        }

        user.setRoles(rolesMapBuilder.getRoleMap());
        return user;
    }

    public Set<JgRole> getRoles(User user) {
        Set<JgRole> userRoles = new HashSet<>();
        for (JgRole role : map.keySet()) {
            CrudRepository<? extends RoleDetails, Long> repo = map.get(role);
            RoleDetails roleDetails = repo.findOne(user.getId());
            if (roleDetails != null) {
                userRoles.add(role);
            }
        }
        return userRoles;
    }
}
