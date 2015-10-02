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

public enum JgRole {
    ADMINISTRATOR(5), MANAGER(4), TEACHER(3), PARENT(2), STUDENT(1);

    private final Integer priority;

    JgRole(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }

    public String withRolePrefix() {
        return "ROLE_" + this.name();
    }
}
