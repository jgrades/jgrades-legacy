package org.jgrades.logging.logger.utils;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jgrades.logging.logger.configuration.LoggingConfiguration;
import org.jgrades.logging.logger.configuration.strategy.ConfigurationStrategy;
import org.jgrades.logging.logger.configuration.strategy.ModuleConfiguration;
import org.jgrades.logging.logger.configuration.strategy.TypeConfiguration;
import org.jgrades.logging.logger.configuration.strategy.TypeModuleConfiguration;
import org.slf4j.MDC;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Piotr on 2015-07-23.
 */
public class PropertyUtils {

    private static final String PROPERTIES_FILE = "jgrades_logger.properties";
    private static final String CURRENT_CONFIGURATION_PROPERTY_FILED = "current.configuration";

    private static Properties prop;

    public synchronized static LoggingConfiguration getCurrentLoggerConfiguration() throws IOException {
        readPropertyFile();
        return LoggingConfiguration.valueOf(prop.getProperty(CURRENT_CONFIGURATION_PROPERTY_FILED));
    }

    public static void setNewLoggerConfiguration(String newConfiguration) throws IOException {
        readPropertyFile();
        Validate.validState(EnumUtils.isValidEnum(LoggingConfiguration.class, newConfiguration));

        prop.setProperty(CURRENT_CONFIGURATION_PROPERTY_FILED, newConfiguration);
    }

    public synchronized static ConfigurationStrategy readConfigurationStrategy(LoggingConfiguration configuration) throws IllegalAccessException {
            switch(configuration) {
                case LOG_PER_TYPE:
                    return new TypeConfiguration();
                case LOG_PER_MODULE:
                    return new ModuleConfiguration();
                case LOG_PER_TYPE_MODULE:
                    return new TypeModuleConfiguration();
                default:
                    throw new IllegalAccessException();
            }
    }

    private static void readPropertyFile() throws IOException {
        prop = new Properties();
        String propFileName = PROPERTIES_FILE;
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
        }
    }



}
