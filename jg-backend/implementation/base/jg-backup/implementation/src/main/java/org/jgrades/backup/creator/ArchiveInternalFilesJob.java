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
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.File;
import java.time.LocalDateTime;

@DisallowConcurrentExecution
public class ArchiveInternalFilesJob implements Job {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(ArchiveInternalFilesJob.class);

    @Value("${backup.internal.files.directory}")
    private String internalFilesDirectory;

    @Value("${backup.logs.files.directory}")
    private String logsFilesDirectory;

    @Autowired
    private BackupEventRepository backupEventRepository;

    private Backup backup;

    private SchedulerContext schedulerContext;

    private ZipUtils zipUtils = new ZipUtils();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        init(context);
        backupInternalFiles();
        backupLogFiles();
    }

    private void init(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        try {
            schedulerContext = context.getScheduler().getContext();
            backup = (Backup) schedulerContext.get("backup");
        } catch (SchedulerException e) {
            LOGGER.error("Error during initialize ArchiveInternalFilesJob. Process stopped", e);
            throw new JobExecutionException(e);
        }
    }

    private void backupInternalFiles() {
        String internalFilesZipName = internalFilesDirectory + File.separator + "internal.bak.tmp";
        backupAndZip("Backup of internal files", internalFilesDirectory, internalFilesZipName);
    }

    private void backupLogFiles() {
        String logsFilesZipName = backup.getPath() + File.separator + "jg-logs-" + backup.getName() + ".zip";
        backupAndZip("Backup of log files", logsFilesDirectory, logsFilesZipName);
    }

    private void backupAndZip(String operation, String destDirectory, String destZipFile) {
        BackupEvent event = getNewEvent(operation);
        try {
            zipUtils.zipFiles(destDirectory, destZipFile);
            updateEventType(event, BackupEventType.FINISHED);
        } catch (Exception e) {
            LOGGER.error("Error during operation: {}. Process will be continued", operation, e);
            schedulerContext.put("backupWarningFlag", true);
            setWarnDetails(event);
        }
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
