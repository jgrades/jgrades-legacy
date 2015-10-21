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

import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.entities.BackupEvent;
import org.jgrades.backup.api.model.RestoreSettings;
import org.jgrades.backup.api.service.BackupManagerService;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/backup", produces = MediaType.APPLICATION_JSON_VALUE)
public class BackupService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(BackupService.class);

    @Autowired
    private BackupManagerService backupManagerService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> makeBackup() {
        LOGGER.debug("Request for new backup");
        backupManagerService.makeNow();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Backup getWithId(@PathVariable Long id) {
        LOGGER.debug("Getting entity with id {}", id);
        return backupManagerService.getWithId(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Backup> getAll() {
        LOGGER.debug("Getting all entities");
        return backupManagerService.getAll();
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<Object> refresh() {
        LOGGER.debug("Refreshing backup metadata according to directory content");
        backupManagerService.refreshBackupDirectory();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        LOGGER.debug("Removing entity with id: {} invoked", id);
        backupManagerService.delete(backupManagerService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/restore/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> restore(@PathVariable Long id, @RequestBody RestoreSettings restoreSettings) {
        LOGGER.debug("Restoring backup with id: {} invoked", id);
        backupManagerService.restore(backupManagerService.getWithId(id), restoreSettings);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/interrupt/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> interrupt(@PathVariable Long id) {
        LOGGER.debug("Interrupting backup with id: {} invoked", id);
        backupManagerService.interruptMaking(backupManagerService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/events", method = RequestMethod.GET)
    @ResponseBody
    public List<BackupEvent> getEvents(@PathVariable Long id) {
        LOGGER.debug("Getting backup events for backup with id: {} invoked", id);
        return backupManagerService.getEvents(backupManagerService.getWithId(id));
    }

}
