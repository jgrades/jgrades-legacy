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

import org.jgrades.admin.api.general.DaysMgntService;
import org.jgrades.admin.api.model.WorkingDays;
import org.jgrades.lic.api.aop.CheckLicence;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.api.admin.general.IWorkingDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.Set;

@RestController
@RequestMapping(value = "/workingdays", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
@CheckLicence
public class WorkingDaysService implements IWorkingDaysService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(WorkingDaysService.class);

    @Autowired
    private DaysMgntService daysMgntService;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public Set<DayOfWeek> getWorkingDays() {
        LOGGER.trace("Getting school working days");
        return daysMgntService.getWorkingDays().getDays();
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','MANAGER')")
    public void setWorkingDays(@RequestBody Set<DayOfWeek> days) {
        LOGGER.trace("Saving or overriding exising school working days with: {}", days);
        WorkingDays workingDays = new WorkingDays();
        days.forEach(workingDays::addDay);
        daysMgntService.setWorkingDays(workingDays);
    }
}
