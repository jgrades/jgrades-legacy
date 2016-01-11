/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.api.accounts;

import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.data.api.service.crud.CrudPagingService;
import org.jgrades.data.api.service.crud.PagingSpecificationSelector;

import java.util.Set;

public interface UserMgntService extends CrudPagingService<User, Long>,
        PagingSpecificationSelector<User, Long> {
    Set<JgRole> getUserRoles(User user);

    User getWithLogin(String login);
}
