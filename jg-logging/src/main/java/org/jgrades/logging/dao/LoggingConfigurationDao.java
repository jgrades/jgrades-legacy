package org.jgrades.logging.dao;

import org.jgrades.logging.model.LoggingConfiguration;

public interface LoggingConfigurationDao {
    LoggingConfiguration getCurrentConfiguration();

    LoggingConfiguration getDefaultConfiguration();

    void setConfiguration(LoggingConfiguration configuration);
}
