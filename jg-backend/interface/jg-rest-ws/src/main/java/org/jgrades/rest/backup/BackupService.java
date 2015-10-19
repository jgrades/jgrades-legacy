/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.backup;

import org.jgrades.backup.api.service.BackupManagerService;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/backup", produces = MediaType.APPLICATION_JSON_VALUE)
public class BackupService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(BackupService.class);

    @Autowired
    private BackupManagerService backupManagerService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> makeBackup() {
        LOGGER.trace("Request for new backup");
        backupManagerService.makeNow();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
