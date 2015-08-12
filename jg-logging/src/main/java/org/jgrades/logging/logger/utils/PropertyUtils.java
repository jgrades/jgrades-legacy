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
    private static Properties internalProperties,externalProperties;

    public synchronized static LoggingConfiguration getCurrentLoggerConfiguration() {
        checkReadPropertyFile();
        return LoggingConfiguration.valueOf(externalProperties.getProperty(CURRENT_CONFIGURATION_PROPERTY_FILE));
    }

    public synchronized static Level getCurrentLoggingLevel(){
        checkReadPropertyFile();
        return Level.valueOf(externalProperties.getProperty(CURRENT_LOGGING_LEVEL));
    }


    public static void setNewLoggingLevel(Level level){
        checkReadPropertyFile();
        externalProperties.setProperty(CURRENT_LOGGING_LEVEL, level.toString());
    }

    public static void setNewLoggerConfiguration(String newConfiguration) {
        checkReadPropertyFile();
        Validate.validState(EnumUtils.isValidEnum(LoggingConfiguration.class, newConfiguration));

        externalProperties.setProperty(CURRENT_CONFIGURATION_PROPERTY_FILE, newConfiguration);


    }

    private static void checkReadPropertyFile(){
        try {
            if (internalProperties == null) {
                internalProperties = new Properties();
                readPropertyFile(internalProperties, Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE));
            }
            if (externalProperties == null) {
                externalProperties = new Properties();
                readPropertyFile(externalProperties, new FileInputStream(new File(internalProperties.getProperty(CURRENT_LOGGING_PROPERTIES_DIR))));
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void readPropertyFile(Properties propertiesFile,InputStream inputToPropertiesFile) {
        String propFileName = PROPERTIES_FILE;

        try {
            if (inputToPropertiesFile != null) {
                propertiesFile.load(inputToPropertiesFile);
            } else {
                throw new FileNotFoundException("Property file '" + propFileName + "' was  not found");
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



}
