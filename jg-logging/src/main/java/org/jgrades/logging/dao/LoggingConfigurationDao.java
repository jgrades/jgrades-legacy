package org.jgrades.logging.dao;

import org.jgrades.logging.model.LoggingConfiguration;

public interface LoggingConfigurationDao {
    LoggingConfiguration getConfiguration();

    void setConfiguration(LoggingConfiguration configuration);

    boolean isConfigurationCustomized();
}
