/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.structures;

import org.jgrades.admin.api.structures.DivisionMgntService;
import org.jgrades.admin.common.AbstractPagingMgntService;
import org.jgrades.data.api.dao.structures.DivisionRepository;
import org.jgrades.data.api.entities.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DivisionMgntServiceImpl extends AbstractPagingMgntService<Division, Long, DivisionRepository> implements DivisionMgntService {
    @Autowired
    public DivisionMgntServiceImpl(DivisionRepository repository) {
        super(repository);
    }
}
