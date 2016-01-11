/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.api.model;

import org.jgrades.data.api.entities.roles.RoleDetails;

import java.util.EnumMap;
import java.util.Map;

public class RolesMap extends EnumMap<JgRole, RoleDetails> {
    public RolesMap(Class<JgRole> keyType) {
        super(keyType);
    }

    public RolesMap(Map<JgRole, ? extends RoleDetails> m) {
        super(m);
    }

    public RolesMap() {
        super(JgRole.class);
    }
}
