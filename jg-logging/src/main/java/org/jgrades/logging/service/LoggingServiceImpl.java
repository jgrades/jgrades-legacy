/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.service;

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.utils.LogbackXmlEditor;

public class LoggingServiceImpl implements LoggingService {
    private LogbackXmlEditor xmlEditor = new LogbackXmlEditor();
    private LoggingConfigurationDao dao = new LoggingConfigurationDaoFileImpl();

    @Override
    public LoggingConfiguration getLoggingConfiguration() {
        return dao.getCurrentConfiguration();
    }

    @Override
    public void setLoggingConfiguration(LoggingConfiguration loggingConfiguration) {
        dao.setConfiguration(loggingConfiguration);
    }

    @Override
    public LoggingConfiguration getDefaultConfiguration() {
        return dao.getDefaultConfiguration();
    }

    @Override
    public boolean isUsingDefaultConfiguration() {
        return !xmlEditor.isXmlExists() ? true : dao.getDefaultConfiguration().equals(dao.getCurrentConfiguration());
    }
}
