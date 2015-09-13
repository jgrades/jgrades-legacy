package org.jgrades.logging.dao;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.jgrades.logging.model.JgLogLevel;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.model.LoggingStrategy;
import org.slf4j.LoggerFactory;

import static org.jgrades.logging.utils.InternalProperties.*;

public class LoggingConfigurationDaoFileImpl implements LoggingConfigurationDao {
    private String externalConfigFilePath;

    public LoggingConfigurationDaoFileImpl() {
        externalConfigFilePath = extractExternalConfigFilePath();
    }

    public LoggingConfigurationDaoFileImpl(String externalConfigFilePath) {
        this.externalConfigFilePath = externalConfigFilePath;
    }

    private String extractExternalConfigFilePath() {
        return CONFIG_FILE;
    }

    private Configuration internalConfiguration() {
        try {
            return new PropertiesConfiguration(INTERNAL_CONFIG_FILE_PATH);
        } catch (ConfigurationException e) {
            // not possible...
        }
        return null;
    }

    private Configuration externalConfiguration() throws ConfigurationException {
        PropertiesConfiguration extConf = new PropertiesConfiguration(externalConfigFilePath);
        extConf.setAutoSave(true);
        return extConf;
    }

    @Override
    public LoggingConfiguration getCurrentConfiguration() {
        try {
            Configuration externalConfig = externalConfiguration();
            return extractConfigurationProperties(externalConfig);
        } catch (ConfigurationException e) {
            return getDefaultConfiguration();
        }
    }

    @Override
    public LoggingConfiguration getDefaultConfiguration() {
        return extractConfigurationProperties(internalConfiguration());
    }

    @Override
    public void setConfiguration(LoggingConfiguration configuration) {
        try {
            Configuration externalConfig = externalConfiguration();
            externalConfig.setProperty(STRATEGY_PROPERTY_NAME, configuration.getLoggingStrategy().toString());
            externalConfig.setProperty(LEVEL_PROPERTY_NAME, configuration.getLevel());
            externalConfig.setProperty(MAX_FILE_SIZE_PROPERTY_NAME, configuration.getMaxFileSize());
            externalConfig.setProperty(MAX_DAYS_PROPERTY_NAME, configuration.getMaxDays());

            ((Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.toLevel(configuration.getLevel().toString()));
        } catch (ConfigurationException e) {
            return;
        }
    }

    private LoggingConfiguration extractConfigurationProperties(Configuration externalConfig) {
        String strategyName = externalConfig.getString(STRATEGY_PROPERTY_NAME);
        String levelName = externalConfig.getString(LEVEL_PROPERTY_NAME);
        String maxSizeValue = externalConfig.getString(MAX_FILE_SIZE_PROPERTY_NAME);
        int maxDaysValue = externalConfig.getInt(MAX_DAYS_PROPERTY_NAME);

        return new LoggingConfiguration(LoggingStrategy.valueOf(strategyName), JgLogLevel.valueOf(levelName),
                maxSizeValue, maxDaysValue);
    }
}
