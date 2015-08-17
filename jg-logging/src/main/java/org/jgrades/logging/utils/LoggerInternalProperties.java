package org.jgrades.logging.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public final class LoggerInternalProperties {
    private static Configuration configuration;

    static {
        try {
            configuration = new PropertiesConfiguration("jg-logging.properties");
        } catch (ConfigurationException e) {
            //not needed...
        }
    }

    public static final String LOGS_DIRECTORY = configuration.getString("logging.logs.directory");
    public static final String CONFIG_FILE = configuration.getString("logging.config.file");
    public static final String XML_FILE = configuration.getString("logging.xml.file");
    public static final String STRATEGY = configuration.getString("logging.strategy");
    public static final String LEVEL = configuration.getString("logging.level");
    public static final String CONSOLE_LEVEL = configuration.getString("logging.console.level");
    public static final String MAX_FILE_SIZE = configuration.getString("logging.max.file.size");
    public static final Integer MAX_DAYS = configuration.getInt("logging.max.days");
    public static final String DEFAULT_XML_FILE =
            LoggerInternalProperties.class.getClassLoader()
            .getResource("logback-default.xml").getFile();

    private LoggerInternalProperties() {
    }
}
