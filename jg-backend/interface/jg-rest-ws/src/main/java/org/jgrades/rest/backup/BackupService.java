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
import org.jgrades.lic.api.aop.CheckLicence;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.rest.api.backup.IBackupService;
import org.jgrades.rest.common.AbstractRestCrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/backup", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckLicence
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class BackupService extends AbstractRestCrudPagingService<Backup, Long, BackupManagerService> implements IBackupService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(BackupService.class);

    @Autowired
    private BackupManagerService backupManagerService;

    @Autowired
    protected BackupService(BackupManagerService crudService) {
        super(crudService);
    }

    @Override
    @ApiIgnore
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertOrUpdate(@RequestBody Backup entity) {
        throw new IllegalStateException("You should PUT method for creating new backup");
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT)
    public void makeBackup() {
        LOGGER.debug("Request for new backup");
        backupManagerService.makeNow();
    }

    @Override
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public void refresh() {
        LOGGER.debug("Refreshing backup metadata according to directory content");
        backupManagerService.refreshBackupDirectory();
    }

    @Override
    @RequestMapping(value = "/{id}/restore", method = RequestMethod.POST)
    public void restore(@PathVariable Long id, @RequestBody RestoreSettings restoreSettings) {
        LOGGER.debug("Restoring backup with id: {} invoked", id);
        backupManagerService.restore(backupManagerService.getWithId(id), restoreSettings);
    }

    @Override
    @RequestMapping(value = "/{id}/interrupt", method = RequestMethod.POST)
    public void interrupt(@PathVariable Long id) {
        LOGGER.debug("Interrupting backup with id: {} invoked", id);
        backupManagerService.interruptMaking(backupManagerService.getWithId(id));
    }

    @Override
    @RequestMapping(value = "/{id}/events", method = RequestMethod.GET)
    public List<BackupEvent> getEvents(@PathVariable Long id) {
        LOGGER.debug("Getting backup events for backup with id: {} invoked", id);
        return backupManagerService.getEvents(backupManagerService.getWithId(id));
    }

    @Override
    protected JgLogger getLogger() {
        return LOGGER;
    }
}
