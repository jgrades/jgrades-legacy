/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.api.utils;

import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.data.api.model.RolesMap;

public class RolesMapBuilder {
    private RolesMap roleMap = new RolesMap();

    public void addRole(JgRole name, RoleDetails details) {
        roleMap.put(name, details);
    }

    public void removeRole(JgRole name) {
        roleMap.remove(name);
    }

    public RolesMap getRoleMap() {
        return roleMap;
    }
}
