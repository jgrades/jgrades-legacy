/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.api.security;

import org.jgrades.data.api.model.JgRole;
import org.jgrades.security.api.entities.PasswordPolicy;

import java.util.Set;

public interface IPasswordService {
    void setPassword(PasswordDTO passwordInfo);

    PasswordPolicy getForRole(JgRole role);

    Set<PasswordPolicy> getAll();

    void setForRole(PasswordPolicy policy);
}
