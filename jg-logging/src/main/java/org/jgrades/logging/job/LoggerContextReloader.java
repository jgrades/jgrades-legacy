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

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.jgrades.logging.utils.InternalProperties;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LoggerContextReloader {
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
                setDefaultConfiguration(configurator);
            }
        } else {
            setDefaultConfiguration(configurator);
        }
    }

    private void setDefaultConfiguration(JoranConfigurator configurator) {
        try {
            configurator.doConfigure(InternalProperties.ONLY_CONSOLE_XML_FILE);
        } catch (JoranException e) {
            // not possible...
        }
    }
}
