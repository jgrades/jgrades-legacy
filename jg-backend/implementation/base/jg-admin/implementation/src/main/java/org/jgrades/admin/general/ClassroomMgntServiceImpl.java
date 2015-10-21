/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.general;

import org.jgrades.admin.api.general.ClassroomMgntService;
import org.jgrades.data.api.dao.ClassroomRepository;
import org.jgrades.data.api.entities.Classroom;
import org.jgrades.data.api.service.crud.AbstractPagingMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomMgntServiceImpl extends AbstractPagingMgntService<Classroom, Long, ClassroomRepository> implements ClassroomMgntService {
    @Autowired
    public ClassroomMgntServiceImpl(ClassroomRepository repository) {
        super(repository);
    }
}
