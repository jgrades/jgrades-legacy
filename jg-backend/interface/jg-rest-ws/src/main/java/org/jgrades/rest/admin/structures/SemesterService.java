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
import org.jgrades.rest.admin.common.AbstractRestCrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> migrate(@PathVariable Long id, @PathVariable String newSemesterName) {
        crudService.createNewByMigration(crudService.getWithId(id), newSemesterName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    @ResponseBody
    public Semester getActive() {
        getLogger().trace("Getting active semester");
        return crudService.getActiveSemester();
    }

    @RequestMapping(value = "/active/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> setActive(@PathVariable Long id) {
        getLogger().trace("Setting as active a semester with id {}", id);
        crudService.setActiveSemester(crudService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    protected JgLogger getLogger() {
        return LOGGER; //NOSONAR
    }
}
