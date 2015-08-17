package org.jgrades.logging.service;

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.utils.LogbackXmlEditor;

public class LoggingServiceImpl implements LoggingService {
    private LogbackXmlEditor xmlEditor = new LogbackXmlEditor();
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
        return !xmlEditor.isXmlExists() || !dao.isConfigurationCustomized();
    }
}
