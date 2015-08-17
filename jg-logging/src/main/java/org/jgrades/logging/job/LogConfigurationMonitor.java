package org.jgrades.logging.job;

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.model.LoggingConfiguration;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class LogConfigurationMonitor implements Job {
    private static LoggingConfiguration cachedConfig;

    private LoggingConfigurationDao dao = new LoggingConfigurationDaoFileImpl();
    private XmlConfigurationUpdater updater = new XmlConfigurationUpdater();
    private LoggerContextReloader reloader = new LoggerContextReloader();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LoggingConfiguration targetConfig = dao.getConfiguration();
        if(cachedConfig != null){
            if(!cachedConfig.equals(targetConfig)){
                processNewConfig(targetConfig);
            }
        } else{
            processNewConfig(targetConfig);
        }
    }

    private void processNewConfig(LoggingConfiguration targetConfig) {
        updater.update(targetConfig);
        cachedConfig = targetConfig;
        reloader.reload();
    }
}
