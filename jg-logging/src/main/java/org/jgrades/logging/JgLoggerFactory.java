package org.jgrades.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.apache.commons.configuration.Configuration;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.job.ExternalConfigurationMonitoringJob;
import org.jgrades.logging.job.XmlConfigurationUpdater;
import org.jgrades.logging.utils.LoggerInternalProperties;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
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

            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(ExternalConfigurationMonitoringJob.class)
                    .withIdentity("extConfMonitorJob", "jg-logging")
                    .build();

            // Trigger the job to run now, and then every 40 seconds
            Trigger trigger = newTrigger()
                    .withIdentity("extConfMonitorTrigger", "jg-logging")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(5)
                            .repeatForever())
                    .build();

            // Tell quartz to schedule the job using our trigger
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
