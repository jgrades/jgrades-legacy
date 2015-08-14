package org.jgrades.logging.logger.utils;

import ch.qos.logback.classic.Level;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.Validate;
import org.jgrades.logging.logger.configuration.LoggingConfiguration;

import java.io.*;
import java.util.Properties;


public class PropertyUtils {

    private static final String PROPERTIES_FILE = "jg-logging.properties";
    private static final String CURRENT_LOGGING_PROPERTIES_DIR = "logging.properties.dir";
    private static final String CURRENT_CONFIGURATION_PROPERTY_FILE = "logging.configuration.strategy";
    private static final String CURRENT_LOGGING_LEVEL = "logging.level";
    private static Properties INTERNAL_PROPERTIES,EXTERNAL_PROPERTIES;

    public synchronized static LoggingConfiguration getCurrentLoggerConfiguration() {
        checkReadPropertyFile();
        if(EnumUtils.isValidEnum(LoggingConfiguration.class,EXTERNAL_PROPERTIES.getProperty(CURRENT_CONFIGURATION_PROPERTY_FILE))){
            return LoggingConfiguration.valueOf(EXTERNAL_PROPERTIES.getProperty(CURRENT_CONFIGURATION_PROPERTY_FILE));
        }
        else {
            return LoggingConfiguration.LOG_PER_TYPE;
        }
    }

    public synchronized static Level getCurrentLoggingLevel(){
        checkReadPropertyFile();
        try {
            return Level.valueOf(EXTERNAL_PROPERTIES.getProperty(CURRENT_LOGGING_LEVEL));
        }
        catch(NullPointerException ex) {
            return Level.INFO;
        }
    }


    public static void setNewLoggingLevel(Level level){
        checkReadPropertyFile();
        EXTERNAL_PROPERTIES.setProperty(CURRENT_LOGGING_LEVEL, level.toString());
    }

    public static void setNewLoggerConfiguration(String newConfiguration) {
        checkReadPropertyFile();
        Validate.validState(EnumUtils.isValidEnum(LoggingConfiguration.class, newConfiguration));

        EXTERNAL_PROPERTIES.setProperty(CURRENT_CONFIGURATION_PROPERTY_FILE, newConfiguration);


    }

    private static void checkReadPropertyFile(){
        try {
            if (INTERNAL_PROPERTIES == null) {
                INTERNAL_PROPERTIES = new Properties();
                readPropertyFile(INTERNAL_PROPERTIES, Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE));
            }
            if (EXTERNAL_PROPERTIES == null) {
                EXTERNAL_PROPERTIES = new Properties();
                readPropertyFile(EXTERNAL_PROPERTIES, new FileInputStream(new File(INTERNAL_PROPERTIES.getProperty(CURRENT_LOGGING_PROPERTIES_DIR))));
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void readPropertyFile(Properties propertiesFile,InputStream inputToPropertiesFile) {
        try {
            if (inputToPropertiesFile != null) {
                propertiesFile.load(inputToPropertiesFile);
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                inputToPropertiesFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setPropertiesFile(Properties file){
        PropertyUtils.INTERNAL_PROPERTIES = new Properties();
        PropertyUtils.EXTERNAL_PROPERTIES = file;
    }



}
