/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.structures;

import org.jgrades.admin.api.structures.YearLevelMgntService;
import org.jgrades.data.api.dao.structures.YearLevelRepository;
import org.jgrades.data.api.entities.YearLevel;
import org.jgrades.data.api.service.crud.AbstractPagingMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YearLevelMgntServiceImpl extends AbstractPagingMgntService<YearLevel, Long, YearLevelRepository> implements YearLevelMgntService {
    @Autowired
    public YearLevelMgntServiceImpl(YearLevelRepository repository) {
        super(repository);
    }
}
