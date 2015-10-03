/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.logging;

import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.service.LoggingService;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/logging")
@CheckSystemDependencies
public class LoggerConfigService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(LoggerConfigService.class);

    @Autowired
    private LoggingService loggingService;

    @RequestMapping(value = "/configuration/default", method = RequestMethod.GET)
    public LoggingConfiguration getDefaultConfiguration() {
        LoggingConfiguration defaultConfiguration = loggingService.getDefaultConfiguration();
        LOGGER.info("Get default logging configuration: {}", defaultConfiguration);
        return defaultConfiguration;
    }

    @RequestMapping(value = "/configuration", method = RequestMethod.GET)
    public LoggingConfiguration getConfiguration() {
        LoggingConfiguration configuration = loggingService.getLoggingConfiguration();
        LOGGER.info("Get current logging configuration: {}", configuration);
        return configuration;
    }

    @RequestMapping(value = "/configuration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setNewConfiguration(@RequestBody LoggingConfiguration configuration) {
        LOGGER.info("Set new logging configuration: {}", configuration);
        loggingService.setLoggingConfiguration(configuration);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
