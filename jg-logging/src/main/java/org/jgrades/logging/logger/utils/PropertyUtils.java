package org.jgrades.logging.logger.utils;

import ch.qos.logback.classic.Level;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.Validate;
import org.jgrades.logging.logger.configuration.LoggingConfiguration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyUtils {

    private static final String PROPERTIES_FILE = "jg-logging.properties";
    private static final String CURRENT_CONFIGURATION_PROPERTY_FILED = "current.configuration";
    private static final String CURRENT_LOGGING_LEVEL = "current.logging.level";
    private static Properties prop;

    public synchronized static LoggingConfiguration getCurrentLoggerConfiguration() {
        checkReadedPropertyFile();
        return LoggingConfiguration.valueOf(prop.getProperty(CURRENT_CONFIGURATION_PROPERTY_FILED));
    }

    public synchronized static Level getCurrentLoggingLevel(){
        checkReadedPropertyFile();
        return Level.valueOf(prop.getProperty(CURRENT_LOGGING_LEVEL));
    }

    public static void setNewLoggingLevel(Level level){
        checkReadedPropertyFile();
        prop.setProperty(CURRENT_LOGGING_LEVEL, level.toString());
    }

    public static void setNewLoggerConfiguration(String newConfiguration) {
        checkReadedPropertyFile();
        Validate.validState(EnumUtils.isValidEnum(LoggingConfiguration.class, newConfiguration));

        prop.setProperty(CURRENT_CONFIGURATION_PROPERTY_FILED, newConfiguration);


    }

    private static void checkReadedPropertyFile(){
        if(prop == null ) {
            readPropertyFile();
        }
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
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
