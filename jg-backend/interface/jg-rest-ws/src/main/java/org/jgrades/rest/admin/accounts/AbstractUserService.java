/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.admin.accounts;

import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.data.api.entities.User;
import org.jgrades.rest.admin.common.AbstractRestCrudPagingService;

public abstract class AbstractUserService<U extends User> extends AbstractRestCrudPagingService<U, Long, UserMgntService<U>> {
    protected AbstractUserService(UserMgntService<U> crudService) {
        super(crudService);
    }
}
