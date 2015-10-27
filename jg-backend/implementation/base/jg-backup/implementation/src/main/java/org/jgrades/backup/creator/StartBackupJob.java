/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.backup.creator;

import org.apache.commons.io.FileUtils;
import org.jgrades.backup.api.dao.BackupEventRepository;
import org.jgrades.backup.api.dao.BackupRepository;
import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.entities.BackupEvent;
import org.jgrades.backup.api.model.BackupEventSeverity;
import org.jgrades.backup.api.model.BackupEventType;
import org.jgrades.backup.api.model.BackupOperation;
import org.jgrades.backup.api.model.BackupStatus;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@DisallowConcurrentExecution
public class StartBackupJob implements Job {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(StartBackupJob.class);

    @Value("${backup.dir}")
    private String backupDirectory;

    @Value("${backup.internal.files.directory}")
    private String internalFilesDirectory;

    @Value("${backup.logs.files.directory}")
    private String logsFilesDirectory;

    @Autowired
    private BackupRepository backupRepository;

    @Autowired
    private BackupEventRepository backupEventRepository;

    private static String getBackupName(LocalDateTime scheduledDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH_mm_ss").format(scheduledDateTime);
    }

    private static void insertBackupToSchedulerContext(JobExecutionContext context, Backup backup)
            throws JobExecutionException {
        try {
            SchedulerContext schedulerContext = context.getScheduler().getContext();
            schedulerContext.put("backup", backup);
            schedulerContext.put("backupWarningFlag", false);
        } catch (SchedulerException e) {
            LOGGER.error("Cannot put values to schedulerContext", e);
            throw new JobExecutionException(e);
        }
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        LocalDateTime scheduledDateTime = LocalDateTime.now();
        String backupName = getBackupName(scheduledDateTime);
        File backupInstanceDir = new File(backupDirectory + File.separator + backupName);

        Backup backup = saveBackupMetadataToDb(scheduledDateTime, backupName, backupInstanceDir);
        BackupEvent event = saveBackupEventMetadataToDb(backup);

        tryToCreateBackupFolder(backupInstanceDir, backup, event);

        insertBackupToSchedulerContext(context, backup);
    }

    private Backup saveBackupMetadataToDb(LocalDateTime scheduledDateTime, String backupName, File backupInstanceDir) {
        Backup backup = new Backup();
        backup.setName(backupName);
        backup.setScheduledDateTime(scheduledDateTime);
        backup.setStatus(BackupStatus.ONGOING);
        backup.setPath(backupInstanceDir.getAbsolutePath());
        backupRepository.save(backup);
        return backup;
    }

    private BackupEvent saveBackupEventMetadataToDb(Backup backup) {
        BackupEvent event = new BackupEvent();
        event.setEventType(BackupEventType.ONGOING);
        event.setSeverity(BackupEventSeverity.INFO);
        event.setOperation(BackupOperation.BACKUPING);
        event.setStartTime(LocalDateTime.now());
        event.setBackup(backup);
        event.setMessage("Creating directory for backup content");
        backupEventRepository.save(event);
        return event;
    }

    private void tryToCreateBackupFolder(File backupInstanceDir, Backup backup, BackupEvent event)
            throws JobExecutionException {
        try {
            FileUtils.forceMkdir(backupInstanceDir);
            event.setEventType(BackupEventType.FINISHED);
            event.setEndTime(LocalDateTime.now());
            backupEventRepository.save(event);
        } catch (IOException e) {
            LOGGER.error("Cannot create directory for backup: {}", backupInstanceDir, e);
            setFailureDetails(backup, event);
            throw new JobExecutionException(e);
        }
    }

    private void setFailureDetails(Backup backup, BackupEvent event) {
        backup.setStatus(BackupStatus.DONE_WITH_ERROR);
        event.setSeverity(BackupEventSeverity.ERROR);
        event.setMessage("Cannot create directory for backup");
        event.setEndTime(LocalDateTime.now());
        backupRepository.save(backup);
        backupEventRepository.save(event);
    }
}
