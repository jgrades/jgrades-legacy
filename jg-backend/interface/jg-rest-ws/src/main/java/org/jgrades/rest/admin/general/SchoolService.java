/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.admin.general;

import org.jgrades.admin.api.general.GeneralDataService;
import org.jgrades.data.api.entities.School;
import org.jgrades.lic.api.aop.CheckLicence;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.api.admin.general.ISchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/general", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
@CheckLicence
public class SchoolService implements ISchoolService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(SchoolService.class);

    @Autowired
    private GeneralDataService generalDataService;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public School getGeneralData() {
        LOGGER.trace("Getting school general details");
        return generalDataService.getSchoolGeneralDetails();
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','MANAGER')")
    public void insertOrUpdate(@RequestBody School generalData) {
        LOGGER.trace("Saving or overriding exising school general details with: {}", generalData);
        generalDataService.setSchoolGeneralDetails(generalData);
    }
}
