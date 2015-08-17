package org.jgrades.logging.service;

import org.apache.commons.lang.NotImplementedException;
import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.model.LoggingConfiguration;

public class LoggingServiceImpl implements LoggingService {
    private LoggingConfigurationDao dao;

    public LoggingServiceImpl() {
        this.dao = new LoggingConfigurationDaoFileImpl();
    }

    public LoggingServiceImpl(LoggingConfigurationDao dao) {
        this.dao = dao;
    }

    @Override
    public LoggingConfiguration getLoggingConfiguration() {
        return dao.getConfiguration();
    }

    @Override
    public void setLoggingConfiguration(LoggingConfiguration loggingConfiguration) {
        dao.setConfiguration(loggingConfiguration);
    }

    @Override
    public boolean isUsingDefaultConfiguration() {
        return !dao.isConfigurationCustomized();
    }
}
