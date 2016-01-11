/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.job;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.logging.utils.InternalProperties;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LoggerContextReloader {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(LoggerContextReloader.class);

    private static void setDefaultConfiguration(JoranConfigurator configurator) {
        try {
            configurator.doConfigure(InternalProperties.ONLY_CONSOLE_XML_FILE);
        } catch (JoranException e) {
            LOGGER.error("Configuration logger with default properties from file {} failed", e);
        }
    }

    public void reload() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);

        File externalXmlFile = new File(InternalProperties.XML_FILE);
        if (externalXmlFile.exists()) {
            try {
                configurator.doConfigure(externalXmlFile);
            } catch (JoranException e) {
                LOGGER.warn("Configuration logger with properties from file {} failed. " +
                        "Default configuration will be used", e);
                setDefaultConfiguration(configurator);
            }
        } else {
            setDefaultConfiguration(configurator);
        }
    }
}
