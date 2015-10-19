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
import org.jgrades.backup.api.dao.BackupRepository;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@DisallowConcurrentExecution
public class ArchiveInternalFilesJob implements Job {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(ArchiveInternalFilesJob.class);

    @Value("${backup.internal.files.directory}")
    private String internalFilesDirectory;

    @Value("${backup.logs.files.directory}")
    private String logsFilesDirectory;

    @Autowired
    private BackupRepository backupRepository;

    @Autowired
    private BackupEventRepository backupEventRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
}
