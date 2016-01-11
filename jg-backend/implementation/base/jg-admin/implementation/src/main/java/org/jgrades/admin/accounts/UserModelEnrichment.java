/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.accounts;

import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.data.api.utils.RolesMapBuilder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class UserModelEnrichment extends AbstractUserDetailsRepositories {

    public User enrichWithRoles(User user) {
        if (user == null) {
            return null;
        }
        RolesMapBuilder rolesMapBuilder = new RolesMapBuilder();

        for (Map.Entry<JgRole, CrudRepository<? extends RoleDetails, Long>> repo : repos.entrySet()) {
            RoleDetails roleDetails = repo.getValue().findOne(user.getId());
            if (roleDetails != null) {
                rolesMapBuilder.addRole(repo.getKey(), roleDetails);
            }
        }

        user.setRoles(rolesMapBuilder.getRoleMap());
        return user;
    }

    public Set<JgRole> getRoles(User user) {
        Set<JgRole> userRoles = new HashSet<>();
        for (Map.Entry<JgRole, CrudRepository<? extends RoleDetails, Long>> repo : repos.entrySet()) {
            RoleDetails roleDetails = repo.getValue().findOne(user.getId());
            if (roleDetails != null) {
                userRoles.add(repo.getKey());
            }
        }
        return userRoles;
    }
}
