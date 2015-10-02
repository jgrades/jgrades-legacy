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

import org.jgrades.admin.api.structures.AcademicYearMgntService;
import org.jgrades.data.api.entities.AcademicYear;
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
@RequestMapping(value = "/academicyear", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class AcademicYearService extends AbstractRestCrudPagingService<AcademicYear, Long, AcademicYearMgntService> {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(AcademicYearService.class);

    @Autowired
    protected AcademicYearService(AcademicYearMgntService crudService) {
        super(crudService);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public
    @ResponseBody
    AcademicYear getActive() {
        return crudService.getActiveAcademicYear();
    }

    @RequestMapping(value = "/active/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> setActive(@PathVariable Long id) {
        crudService.setActiveAcademicYear(crudService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
