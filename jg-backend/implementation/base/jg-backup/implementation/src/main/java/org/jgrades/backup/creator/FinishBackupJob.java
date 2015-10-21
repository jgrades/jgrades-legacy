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

import org.jgrades.backup.api.dao.BackupRepository;
import org.jgrades.backup.api.entities.Backup;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import static org.jgrades.backup.api.model.BackupStatus.DONE;
import static org.jgrades.backup.api.model.BackupStatus.DONE_WITH_WARNING;

@DisallowConcurrentExecution
public class FinishBackupJob implements Job {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(FinishBackupJob.class);

    @Autowired
    private BackupRepository backupRepository;

    private Backup backup;

    private SchedulerContext schedulerContext;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        init(context);

        boolean backupResult = (boolean) schedulerContext.get("backupWarningFlag");
        backup.setStatus(backupResult ? DONE_WITH_WARNING : DONE);
        backupRepository.save(backup);
    }

    private void init(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        try {
            schedulerContext = context.getScheduler().getContext();
            backup = (Backup) schedulerContext.get("backup");
        } catch (SchedulerException e) {
            LOGGER.error("Error during initialize FinishBackupJob. Process stopped", e);
            throw new JobExecutionException(e);
        }
    }
}
