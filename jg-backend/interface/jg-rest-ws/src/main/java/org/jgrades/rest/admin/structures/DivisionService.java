/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.admin.structures;

import org.jgrades.admin.api.structures.DivisionMgntService;
import org.jgrades.data.api.entities.Division;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.common.AbstractRestCrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/division", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class DivisionService extends AbstractRestCrudPagingService<Division, Long, DivisionMgntService> {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(DivisionService.class);

    @Autowired
    protected DivisionService(DivisionMgntService crudService) {
        super(crudService);
    }

    @Override
    protected JgLogger getLogger() {
        return LOGGER; //NOSONAR
    }
}
