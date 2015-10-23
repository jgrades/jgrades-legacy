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

import org.jgrades.admin.api.structures.SemesterMgntService;
import org.jgrades.data.api.entities.Semester;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.common.AbstractRestCrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/semester", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class SemesterService extends AbstractRestCrudPagingService<Semester, Long, SemesterMgntService> {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(SemesterService.class);

    @Autowired
    protected SemesterService(SemesterMgntService crudService) {
        super(crudService);
    }

    @RequestMapping(value = "/migrate/{id}/{newSemesterName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void migrate(@PathVariable Long id, @PathVariable String newSemesterName) {
        crudService.createNewByMigration(crudService.getWithId(id), newSemesterName);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public Semester getActive() {
        getLogger().trace("Getting active semester");
        return crudService.getActiveSemester();
    }

    @RequestMapping(value = "/{id}/active", method = RequestMethod.POST)
    public void setActive(@PathVariable Long id) {
        getLogger().trace("Setting as active a semester with id {}", id);
        crudService.setActiveSemester(crudService.getWithId(id));
    }

    @Override
    protected JgLogger getLogger() {
        return LOGGER; //NOSONAR
    }
}
