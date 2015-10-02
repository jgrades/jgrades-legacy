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

import org.jgrades.admin.api.accounts.ManagerMgntService;
import org.jgrades.data.api.dao.accounts.ManagerRepository;
import org.jgrades.data.api.entities.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerMgntServiceImpl extends AbstractUserMgntServiceImpl<Manager, ManagerRepository> implements ManagerMgntService {
    @Autowired
    public ManagerMgntServiceImpl(ManagerRepository repository) {
        super(repository);
    }
}
