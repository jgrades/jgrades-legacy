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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.File;
import java.io.IOException;

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

    private static String getBackupName(DateTime scheduledDateTime) {
        return DateTimeFormat.forPattern("yyyy-MM-dd'T'HH_mm_ss").print(scheduledDateTime);
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        DateTime scheduledDateTime = DateTime.now();
        String backupName = getBackupName(scheduledDateTime);
        File backupInstanceDir = new File(backupDirectory + File.separator + backupName);

        Backup backup = new Backup();
        backup.setName(backupName);
        backup.setScheduledDateTime(scheduledDateTime);
        backup.setStatus(BackupStatus.ONGOING);
        backup.setPath(backupInstanceDir.getAbsolutePath());

        BackupEvent event = new BackupEvent();
        event.setEventType(BackupEventType.STARTED);
        event.setSeverity(BackupEventSeverity.INFO);
        event.setOperation(BackupOperation.BACKUPING);
        event.setTimestamp(DateTime.now());
        event.setBackup(backup);
        event.setMessage("Creating directory for backup content");

        try {
            FileUtils.forceMkdir(backupInstanceDir);
        } catch (IOException e) {
            LOGGER.error("Cannot create directory for backup: {}", backupInstanceDir, e);
            setFailureDetails(backup, event);
            throw new JobExecutionException(e);
        }
        backupRepository.save(backup);
        backupEventRepository.save(event);

        try {
            context.getScheduler().getContext().put("backup", backup);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void setFailureDetails(Backup backup, BackupEvent event) {
        backup.setStatus(BackupStatus.DONE_WITH_ERROR);
        event.setSeverity(BackupEventSeverity.ERROR);
        event.setMessage("Cannot create directory for backup");
        backupRepository.save(backup);
        backupEventRepository.save(event);
    }
}
