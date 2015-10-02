/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.admin.general;

import org.jgrades.admin.api.general.PeriodsMgntService;
import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.admin.common.AbstractRestCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/period", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class PeriodsService extends AbstractRestCrudService<SchoolDayPeriod, Long, PeriodsMgntService> {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(PeriodsService.class);

    @Autowired
    protected PeriodsService(PeriodsMgntService crudService) {
        super(crudService);
    }

    @RequestMapping(value = "/generator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertWithGenerator(@RequestBody PeriodsGeneratorSettings generationSettings) {
        List<SchoolDayPeriod> periods = crudService.generateManyWithGenerator(generationSettings);
        crudService.saveMany(periods);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
