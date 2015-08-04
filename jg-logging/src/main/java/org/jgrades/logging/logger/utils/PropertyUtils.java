package org.jgrades.logging.logger.utils;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.Validate;
import org.jgrades.logging.logger.configuration.LoggingConfiguration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyUtils {

    private static final String PROPERTIES_FILE = "jg-logging.properties";
    private static final String CURRENT_CONFIGURATION_PROPERTY_FILED = "current.configuration";

    private static Properties prop;

    public synchronized static LoggingConfiguration getCurrentLoggerConfiguration() {
        readPropertyFile();
        return LoggingConfiguration.valueOf(prop.getProperty(CURRENT_CONFIGURATION_PROPERTY_FILED));
    }

    public static void setNewLoggerConfiguration(String newConfiguration) {
        readPropertyFile();
        Validate.validState(EnumUtils.isValidEnum(LoggingConfiguration.class, newConfiguration));

        prop.setProperty(CURRENT_CONFIGURATION_PROPERTY_FILED, newConfiguration);
    }

    private static void readPropertyFile() {
        prop = new Properties();
        String propFileName = PROPERTIES_FILE;
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName);

        try {

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }



}
