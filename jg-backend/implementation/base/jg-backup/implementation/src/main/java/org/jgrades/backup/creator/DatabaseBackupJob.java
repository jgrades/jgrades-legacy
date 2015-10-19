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
import org.joda.time.DateTime;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.File;

@DisallowConcurrentExecution
public class DatabaseBackupJob implements Job {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(DatabaseBackupJob.class);

    @Autowired
    private BackupEventRepository backupEventRepository;

    @Autowired
    private DataSourceService dataSourceService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Backup backup = null;
        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = context.getScheduler().getContext();
            backup = (Backup) schedulerContext.get("backup");

            if (!dataSourceService.testConnection()) {

            }

            DataSourceDetails dataSourceDetails = dataSourceService.getDataSourceDetails();

            File databaseDump = new File(backup.getPath() + File.separator + "jg-db-" + backup.getName() + ".zip");

            String[] cmd = {
                    "pg_dump",
                    "--host", "localhost",
                    "--port", "5432",
                    "--username", "postgres",
                    "--dbname", "jgradesdb",
                    "--password", "postgres",
                    "--format", "t",
                    "--verbose",
                    databaseDump.getAbsolutePath()
            };

            Runtime r = Runtime.getRuntime();
            r.exec(cmd);
        } catch (Exception e) {
            LOGGER.error("Error during creating backup of database. Process will be continued", e);
            schedulerContext.put("backupWarningFlag", true);
            setWarnDetails(backup);
        }
    }

    private void saveOkEvent(Backup backup, String message) {
        BackupEvent event = new BackupEvent();
        event.setEventType(BackupEventType.ONGOING);
        event.setSeverity(BackupEventSeverity.INFO);
        event.setOperation(BackupOperation.BACKUPING);
        event.setTimestamp(DateTime.now());
        event.setBackup(backup);
        event.setMessage(message);
        backupEventRepository.save(event);
    }

    private void setWarnDetails(Backup backup) {
        BackupEvent event = new BackupEvent();
        event.setEventType(BackupEventType.ONGOING);
        event.setSeverity(BackupEventSeverity.WARNING);
        event.setOperation(BackupOperation.BACKUPING);
        event.setTimestamp(DateTime.now());
        event.setBackup(backup);
        event.setMessage("Error during creating Backup of internal/logs files. Process will be continued. " +
                "For more details please check application logs");
        backupEventRepository.save(event);
    }
}
