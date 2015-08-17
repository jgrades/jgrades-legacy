package org.jgrades.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.job.ExternalConfigurationMonitoringJob;
import org.jgrades.logging.job.XmlConfigurationUpdater;
import org.jgrades.logging.utils.LoggerInternalProperties;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public final class JgLoggerFactory {
    private static XmlConfigurationUpdater xmlUpdater;
    private static LoggingConfigurationDaoFileImpl dao;

    private JgLoggerFactory() {
    }

    static {
        xmlUpdater = new XmlConfigurationUpdater();
        dao = new LoggingConfigurationDaoFileImpl();
        xmlUpdater.update(dao.getConfiguration());

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);

        File externalXmlFile = new File(LoggerInternalProperties.XML_FILE);
        if(externalXmlFile.exists()){
            try {
                configurator.doConfigure(externalXmlFile);
            } catch (JoranException e) {
                setDefaultConfiguration(configurator);
            }
        } else{
            setDefaultConfiguration(configurator);
        }

        try {
            SchedulerFactory schedFact = new StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            sched.start();

            JobDetail job = newJob(ExternalConfigurationMonitoringJob.class)
                    .withIdentity("extConfMonitorJob", "jg-logging")
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("extConfMonitorTrigger", "jg-logging")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(5)
                            .repeatForever())
                    .build();

            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private static void setDefaultConfiguration(JoranConfigurator configurator){
        try {
            configurator.doConfigure(LoggerInternalProperties.DEFAULT_XML_FILE);
        } catch (JoranException e) {
            // not needed...
        }
    }

    public static JgLogger getLogger(Class clazz) {
        return new JgLogger(clazz);
    }
}
