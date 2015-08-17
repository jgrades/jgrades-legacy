package org.jgrades.logging;

import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.job.LoggerContextReloader;
import org.jgrades.logging.job.MonitorStarter;
import org.jgrades.logging.job.XmlConfigurationUpdater;

public final class JgLoggerFactory {
    private static XmlConfigurationUpdater xmlUpdater;
    private static LoggingConfigurationDaoFileImpl dao;

    static {
        xmlUpdater = new XmlConfigurationUpdater();
        dao = new LoggingConfigurationDaoFileImpl();
        xmlUpdater.update(dao.getConfiguration());

        LoggerContextReloader loggerContextReloader = new LoggerContextReloader();
        loggerContextReloader.reload();

        MonitorStarter monitorStarter = new MonitorStarter();
        monitorStarter.start();
    }

    private JgLoggerFactory() {
    }

    public static JgLogger getLogger(Class clazz) {
        return new JgLogger(clazz);
    }
}
