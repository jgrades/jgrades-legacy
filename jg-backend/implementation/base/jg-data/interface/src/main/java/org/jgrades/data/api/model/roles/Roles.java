/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.api.model.roles;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class Roles {
    private Map<JgRole, RoleDetails> roleMap = Maps.newEnumMap(JgRole.class);

    public void addRole(JgRole name, RoleDetails details) {
        roleMap.put(name, details);
    }

    public void removeRole(JgRole name) {
        roleMap.remove(name);
    }

    public Map<JgRole, RoleDetails> getRoleMap() {
        return ImmutableMap.copyOf(roleMap);
    }
}
