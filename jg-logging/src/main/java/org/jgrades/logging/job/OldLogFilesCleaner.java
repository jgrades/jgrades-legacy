/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.job;

import org.apache.commons.io.FileUtils;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.logging.utils.InternalProperties;
import org.jgrades.logging.utils.OldLogFileFilter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;

public class OldLogFilesCleaner implements Job {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(OldLogFilesCleaner.class);

    private OldLogFileFilter filter = new OldLogFileFilter();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        File logDir = new File(InternalProperties.LOGS_DIRECTORY);
        LOGGER.info("Searching for obsolete log files");
        File[] filesToRemove = logDir.listFiles(filter);
        for (File oldFile : filesToRemove) {
            boolean result = FileUtils.deleteQuietly(oldFile);
            LOGGER.info("Obsolete log file {} removed: {}", oldFile.getName(), result);
        }
    }
}
