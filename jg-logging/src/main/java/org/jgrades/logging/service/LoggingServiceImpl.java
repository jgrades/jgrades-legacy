package org.jgrades.logging.service;

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.utils.LogbackXmlEditor;

public class LoggingServiceImpl implements LoggingService {
    private LogbackXmlEditor xmlEditor = new LogbackXmlEditor();
    private LoggingConfigurationDao dao = new LoggingConfigurationDaoFileImpl();

    @Override
    public LoggingConfiguration getLoggingConfiguration() {
        return dao.getCurrentConfiguration();
    }

    @Override
    public void setLoggingConfiguration(LoggingConfiguration loggingConfiguration) {
        dao.setConfiguration(loggingConfiguration);
    }

    @Override
    public boolean isUsingDefaultConfiguration() {
        return !xmlEditor.isXmlExists() ? true : dao.getDefaultConfiguration().equals(dao.getCurrentConfiguration());
    }
}
