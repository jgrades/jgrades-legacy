package org.jgrades.logging.service;

import org.jgrades.logging.model.LoggingConfiguration;

public interface LoggingService {
    LoggingConfiguration getLoggingConfiguration();

    void setLoggingConfiguration(LoggingConfiguration loggingConfiguration);

    boolean isUsingDefaultConfiguration();
}
