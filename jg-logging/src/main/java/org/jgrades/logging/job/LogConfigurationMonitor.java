package org.jgrades.logging.job;

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.model.LoggingConfiguration;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class LogConfigurationMonitor implements Job {
    private static LoggingConfiguration cachedConfig;

    private LoggingConfigurationDao configurationDao = new LoggingConfigurationDaoFileImpl();
    private XmlConfigurationUpdater configurationUpdater = new XmlConfigurationUpdater();
    private LoggerContextReloader contextReloader = new LoggerContextReloader();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LoggingConfiguration targetConfig = configurationDao.getCurrentConfiguration();
        if(cachedConfig == null || !cachedConfig.equals(targetConfig)){
            processNewConfig(targetConfig);
        }
    }

    private void processNewConfig(LoggingConfiguration targetConfig) {
        configurationUpdater.update(targetConfig);
        cachedConfig = targetConfig;
        contextReloader.reload();
    }
}
