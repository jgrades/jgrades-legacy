package org.jgrades.logging;

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.job.JobsStarter;
import org.jgrades.logging.job.XmlConfigurationUpdater;

public final class JgLoggerFactory {
    private static XmlConfigurationUpdater xmlUpdater;
    private static LoggingConfigurationDao configurationDao;
    private static JobsStarter jobsStarter;

    static {
        xmlUpdater = new XmlConfigurationUpdater();
        configurationDao = new LoggingConfigurationDaoFileImpl();
        jobsStarter = new JobsStarter();

        xmlUpdater.update(configurationDao.getCurrentConfiguration());
        jobsStarter.start();
    }

    private JgLoggerFactory() {
    }

    public static JgLogger getLogger(Class clazz) {
        return new JgLogger(clazz);
    }

}
