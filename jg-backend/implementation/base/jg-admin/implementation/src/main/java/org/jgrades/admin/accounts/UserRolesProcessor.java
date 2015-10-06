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
import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.admin.api.exception.UserRoleViolationException;
import org.jgrades.data.api.dao.accounts.*;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.ParentDetails;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.entities.roles.StudentDetails;
import org.jgrades.data.api.model.JgRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

@Component
public class UserRolesProcessor {//TODO refactored
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMgntService userMgntService;

    private Map<JgRole, CrudRepository<? extends RoleDetails, Long>> repos;

    @PostConstruct
    private void fillMap() {
        repos = Maps.newEnumMap(JgRole.class);
        repos.put(JgRole.ADMINISTRATOR, administratorRepository);
        repos.put(JgRole.MANAGER, managerRepository);
        repos.put(JgRole.TEACHER, teacherRepository);
        repos.put(JgRole.STUDENT, studentRepository);
        repos.put(JgRole.PARENT, parentRepository);
    }

    @Transactional("mainTransactionManager")
    public void process(User user) {
        EnumMap<JgRole, RoleDetails> userRoles = user.getRoles();
        if (userRoles != null && (userRoles.containsKey(JgRole.STUDENT) || userRoles.containsKey(JgRole.PARENT)) && userRoles.size() > 1) {
            throw new UserRoleViolationException();
        }
        boolean forbidden = forbiddenToAddOrDeleteRoles(user);
        for (JgRole role : repos.keySet()) {
            processForRole(user, role, forbidden);
        }
    }

    private boolean forbiddenToAddOrDeleteRoles(User user) {
        Set<JgRole> actualRoles = userMgntService.getUserRoles(user);
        return actualRoles.contains(JgRole.STUDENT) || actualRoles.contains(JgRole.PARENT);
    }

    private void processForRole(User user, JgRole role, boolean onlyUpdateDetailsAllowed) {
        EnumMap<JgRole, RoleDetails> userRoles = user.getRoles();
        Long userId = user.getId();
        CrudRepository repo = repos.get(role);

        if (userRoles != null && userRoles.containsKey(role)) {
            RoleDetails roleDetails = userRoles.get(role);
            if (userHasAlreadyGivenRole(userId, repo)) {
                mapper.map(roleDetails, repo.findOne(userId));
            } else {
                if (onlyUpdateDetailsAllowed) {
                    throw new UserRoleViolationException();
                }
                if (role == JgRole.STUDENT) {
                    ParentDetails parentDetails = new ParentDetails();
                    User parent = new User();
                    parent.setLogin(user.getLogin() + "R");
                    parentDetails.setUser(parent);
                    parentDetails.setStudent((StudentDetails) roleDetails);
                    userMgntService.saveOrUpdate(parent);
                    ((StudentDetails) roleDetails).setParent(parentDetails);
                    parentRepository.save(parentDetails);
                } else if (role == JgRole.PARENT) {
                    throw new UserRoleViolationException();
                }
                roleDetails.setUser(user);
                repo.save(roleDetails);
            }
        } else if (userHasAlreadyGivenRole(userId, repo)) {
            if (role == JgRole.STUDENT || role == JgRole.PARENT) {
                throw new UserRoleViolationException();
            }
            repo.delete(userId);
        }
    }

    private boolean userHasAlreadyGivenRole(Long userId, CrudRepository repo) {
        return repo.exists(userId);
    }

    @Transactional("mainTransactionManager")
    public void remove(User user) {
        if (user == null) {
            return;
        }
        Set<JgRole> actualRoles = userMgntService.getUserRoles(user);
        for (JgRole role : actualRoles) {
            CrudRepository repo = repos.get(role);
            RoleDetails roleDetails = (RoleDetails) repo.findOne(user.getId());
            repo.delete(user.getId());

            if (role == JgRole.STUDENT) {
                Long parentId = ((StudentDetails) roleDetails).getParent().getId();
                parentRepository.delete(parentId);
                userRepository.delete(parentId);
            } else if (role == JgRole.PARENT) {
                Long studentId = ((ParentDetails) roleDetails).getStudent().getId();
                studentRepository.delete(studentId);
                userRepository.delete(studentId);
            }
        }
    }
}
