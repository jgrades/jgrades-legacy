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
import org.jgrades.rest.api.admin.structures.IAcademicYearService;
import org.jgrades.rest.common.AbstractRestCrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/academicyear", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class AcademicYearService extends AbstractRestCrudPagingService<AcademicYear, Long, AcademicYearMgntService> implements IAcademicYearService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(AcademicYearService.class);

    @Autowired
    protected AcademicYearService(AcademicYearMgntService crudService) {
        super(crudService);
    }

    @Override
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public AcademicYear getActive() {
        getLogger().trace("Getting active academic year");
        return crudService.getActiveAcademicYear();
    }

    @Override
    @RequestMapping(value = "/{id}/active", method = RequestMethod.POST)
    public void setActive(@PathVariable Long id) {
        getLogger().trace("Setting as active an academic year with id {}", id);
        crudService.setActiveAcademicYear(crudService.getWithId(id));
    }

    @Override
    protected JgLogger getLogger() {
        return LOGGER; //NOSONAR
    }
}
