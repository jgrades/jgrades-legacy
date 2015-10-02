/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public final class InternalProperties {
    public static final String INTERNAL_CONFIG_FILE_PATH = "jg-logging.properties";
    public static final String STRATEGY_PROPERTY_NAME = "logging.strategy";
    public static final String LEVEL_PROPERTY_NAME = "logging.level";
    public static final String MAX_FILE_SIZE_PROPERTY_NAME = "logging.max.file.size";
    public static final String MAX_DAYS_PROPERTY_NAME = "logging.max.days";
    public static final String ONLY_CONSOLE_XML_FILE =
            InternalProperties.class.getClassLoader()
                    .getResource("logback-only-console-details.xml").getFile();
    private static Configuration configuration;
    public static final String LOGS_DIRECTORY = configuration.getString("logging.logs.directory");
    public static final String CONFIG_FILE = configuration.getString("logging.config.file");
    public static final String XML_FILE = configuration.getString("logging.xml.file");
    public static final String CONSOLE_LEVEL = configuration.getString("logging.console.level");

    static {
        try {
            configuration = new PropertiesConfiguration(INTERNAL_CONFIG_FILE_PATH);
        } catch (ConfigurationException e) {
            //not possible...
        }
    }

    private InternalProperties() {
    }
}
