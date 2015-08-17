package org.jgrades.logging.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public final class InternalProperties {
    private static Configuration configuration;
    public static final String INTERNAL_CONFIG_FILE_PATH = "jg-logging.properties";

    static {
        try {
            configuration = new PropertiesConfiguration(INTERNAL_CONFIG_FILE_PATH);
        } catch (ConfigurationException e) {
            //not possible...
        }
    }

    public static final String STRATEGY_PROPERTY_NAME = "logging.strategy";
    public static final String LEVEL_PROPERTY_NAME = "logging.level";
    public static final String MAX_FILE_SIZE_PROPERTY_NAME = "logging.max.file.size";
    public static final String MAX_DAYS_PROPERTY_NAME = "logging.max.days";

    public static final String LOGS_DIRECTORY = configuration.getString("logging.logs.directory");
    public static final String CONFIG_FILE = configuration.getString("logging.config.file");
    public static final String XML_FILE = configuration.getString("logging.xml.file");
    public static final String CONSOLE_LEVEL = configuration.getString("logging.console.level");

    public static final String ONLY_CONSOLE_XML_FILE =
            InternalProperties.class.getClassLoader()
                    .getResource("logback-only-console-details.xml").getFile();

    private InternalProperties() {
    }
}
