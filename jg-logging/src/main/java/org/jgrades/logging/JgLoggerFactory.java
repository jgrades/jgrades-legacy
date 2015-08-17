package org.jgrades.logging;

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.job.MonitorStarter;
import org.jgrades.logging.job.XmlConfigurationUpdater;

public final class JgLoggerFactory {
    private static XmlConfigurationUpdater xmlUpdater;
    private static LoggingConfigurationDao configurationDao;
    private static MonitorStarter monitorStarter;

    static {
        xmlUpdater = new XmlConfigurationUpdater();
        configurationDao = new LoggingConfigurationDaoFileImpl();
        monitorStarter = new MonitorStarter();

        xmlUpdater.update(configurationDao.getCurrentConfiguration());
        monitorStarter.start();
    }

    public static JgLogger getLogger(Class clazz) {
        return new JgLogger(clazz);
    }

    private JgLoggerFactory() {
    }

}
