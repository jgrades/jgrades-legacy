/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.api.dao;

import org.jgrades.data.api.model.roles.JgRole;
import org.jgrades.security.api.entities.PasswordPolicy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordPolicyRepository extends CrudRepository<PasswordPolicy, JgRole> {
}
