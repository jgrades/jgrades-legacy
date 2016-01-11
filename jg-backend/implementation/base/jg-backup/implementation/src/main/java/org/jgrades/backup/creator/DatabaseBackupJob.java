/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.backup.creator;

import org.jgrades.backup.api.dao.BackupEventRepository;
import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.entities.BackupEvent;
import org.jgrades.backup.api.model.BackupEventSeverity;
import org.jgrades.backup.api.model.BackupEventType;
import org.jgrades.backup.api.model.BackupOperation;
import org.jgrades.data.api.model.DataSourceDetails;
import org.jgrades.data.api.service.DataSourceService;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDateTime;

@DisallowConcurrentExecution
public class DatabaseBackupJob implements Job {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(DatabaseBackupJob.class);

    @Autowired
    private BackupEventRepository backupEventRepository;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private DatabaseBackupCreator databaseBackupCreator;

    private Backup backup;

    private SchedulerContext schedulerContext;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        init(context);
        BackupEvent event = getNewEvent("Backup database");
        try {
            tryToConnectWithDb();

            runDbBackup();

            updateEventType(event, BackupEventType.FINISHED);
        } catch (Exception e) {
            LOGGER.error("Error during creating backup of database. Process will be continued", e);
            schedulerContext.put("backupWarningFlag", true);
            setWarnDetails(event);
        }
    }

    private void init(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        try {
            schedulerContext = context.getScheduler().getContext();
            backup = (Backup) schedulerContext.get("backup");
        } catch (SchedulerException e) {
            LOGGER.error("Error during initialize DatabaseBackupJob. Process stopped", e);
            throw new JobExecutionException(e);
        }
    }

    private void tryToConnectWithDb() throws ConnectException {
        if (!dataSourceService.testConnection()) {
            throw new ConnectException("Test of connection to database failed");
        }
    }

    private void runDbBackup() throws IOException {
        DataSourceDetails dataSourceDetails = dataSourceService.getDataSourceDetails();
        String dbDumpPath = backup.getPath() + File.separator + "jg-db-" + backup.getName() + ".tar";
        databaseBackupCreator.runDbBackup(dataSourceDetails, dbDumpPath);
    }

    private BackupEvent getNewEvent(String message) {
        BackupEvent event = new BackupEvent();
        event.setEventType(BackupEventType.ONGOING);
        event.setSeverity(BackupEventSeverity.INFO);
        event.setOperation(BackupOperation.BACKUPING);
        event.setStartTime(LocalDateTime.now());
        event.setBackup(backup);
        event.setMessage(message);
        backupEventRepository.save(event);
        return event;
    }

    private void updateEventType(BackupEvent event, BackupEventType backupEventType) {
        event.setEventType(backupEventType);
        event.setEndTime(LocalDateTime.now());
        backupEventRepository.save(event);
    }

    private void setWarnDetails(BackupEvent event) {
        event.setEventType(BackupEventType.FINISHED);
        event.setSeverity(BackupEventSeverity.WARNING);
        event.setEndTime(LocalDateTime.now());
        backupEventRepository.save(event);
    }
}
