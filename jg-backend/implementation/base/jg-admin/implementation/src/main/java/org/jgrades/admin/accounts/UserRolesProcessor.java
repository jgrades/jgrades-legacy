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

import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.admin.api.exception.UserRoleViolationException;
import org.jgrades.data.api.dao.accounts.UserRepository;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.ParentDetails;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.entities.roles.StudentDetails;
import org.jgrades.data.api.model.JgRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.Set;

@Component
public class UserRolesProcessor extends AbstractUserDetailsRepositories {//TODO refactored
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMgntService userMgntService;

    private static boolean userHasAlreadyGivenRole(Long userId, CrudRepository repo) {
        return repo.exists(userId);
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
