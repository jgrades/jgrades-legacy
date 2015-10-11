/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.data;

import org.jgrades.data.api.model.DataSourceDetails;
import org.jgrades.data.api.service.DataSourceService;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.monitor.api.model.SystemDependency;
import org.jgrades.monitor.api.service.SystemStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Timer;

@RestController
@RequestMapping(value = "/datasource", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies(ignored = SystemDependency.MAIN_DATA_SOURCE)
public class DataSourceDetailsService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(DataSourceDetailsService.class);

    @Value("${rest.wait.for.restart.miliseconds}")
    private Integer restartDelay;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private SystemStateService systemStateService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public DataSourceDetails getDataSourceDetails() {
        LOGGER.trace("Getting data source details");
        return dataSourceService.getDataSourceDetails();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DataSourceDetails> setDataSourceDetails(@RequestBody DataSourceDetails details) {
        LOGGER.trace("Setting a data source details: {}", details);
        dataSourceService.setDataSourceDetails(details);
        runRestartProcedureAfterDelay();
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    private void runRestartProcedureAfterDelay() {
        new Timer().schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        systemStateService.restartApplication();
                    }
        }, restartDelay);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Boolean> testConnection() {
        LOGGER.trace("Making test of connection to database");
        boolean connectionEstabished = dataSourceService.testConnection();
        HttpStatus httpStatus = connectionEstabished ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(connectionEstabished, httpStatus);
    }
}
