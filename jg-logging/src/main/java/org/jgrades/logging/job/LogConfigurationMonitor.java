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

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.model.LoggingConfiguration;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class LogConfigurationMonitor implements Job {
    private static LoggingConfiguration cachedConfig;

    private LoggingConfigurationDao configurationDao = new LoggingConfigurationDaoFileImpl();
    private XmlConfigurationUpdater configurationUpdater = new XmlConfigurationUpdater();
    private LoggerContextReloader contextReloader = new LoggerContextReloader();

    private synchronized static void updateCache(LoggingConfiguration targetConfig) {
        cachedConfig = targetConfig;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LoggingConfiguration targetConfig = configurationDao.getCurrentConfiguration();
        if (cachedConfig == null || !cachedConfig.equals(targetConfig)) {
            processNewConfig(targetConfig);
        }
    }

    private void processNewConfig(LoggingConfiguration targetConfig) {
        configurationUpdater.update(targetConfig);
        updateCache(targetConfig);
        contextReloader.reload();
    }
}
