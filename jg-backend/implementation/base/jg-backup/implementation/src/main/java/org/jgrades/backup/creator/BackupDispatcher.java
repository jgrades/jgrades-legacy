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

import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.exception.BackupException;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;

@Component
public class BackupDispatcher {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(BackupDispatcher.class);

    @Autowired
    @Qualifier("backupScheduler")
    private Scheduler scheduler;

    public void reqestNew() {
        try {
            checkIsBackupPossible();
            scheduler.clear();

            JobDetail startBackupJob = newJob(StartBackupJob.class)
                    .storeDurably(true).withIdentity("StartBackupJob").build();
            JobDetail archiveInternalFilesJob = newJob(ArchiveInternalFilesJob.class)
                    .storeDurably(true).withIdentity("ArchiveInternalFilesJob").build();
            JobDetail encryptArchiveJob = newJob(EncryptArchiveJob.class)
                    .storeDurably(true).withIdentity("EncryptArchiveJob").build();
            JobDetail databaseBackupJob = newJob(DatabaseBackupJob.class)
                    .storeDurably(true).withIdentity("DatabaseBackupJob").build();
            JobDetail finishBackupJob = newJob(FinishBackupJob.class)
                    .storeDurably(true).withIdentity("FinishBackupJob").build();

            scheduler.addJob(startBackupJob, true);
            scheduler.addJob(archiveInternalFilesJob, true);
            scheduler.addJob(encryptArchiveJob, true);
            scheduler.addJob(databaseBackupJob, true);
            scheduler.addJob(finishBackupJob, true);

            scheduler.triggerJob(JobKey.jobKey("StartBackupJob"));
        } catch (SchedulerException e) {
            throw new BackupException("Some problem with scheduler", e);
        }

    }

    private void checkIsBackupPossible() throws SchedulerException {
        if (!scheduler.getCurrentlyExecutingJobs().isEmpty()) {
            throw new BackupException("Creating another backup is in progress. " +
                    "Please cancel or wait for completing.");
        }
    }

    public void interrupt(Backup backup) {
        throw new UnsupportedOperationException();
    }
}
