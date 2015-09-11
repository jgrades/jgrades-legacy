package org.jgrades.logging.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class JobsStarter {
    public static final String CONF_UPDATER_JOB_NAME = "extConfMonitorJob";
    public static final String CONF_UPDATER_JOB_TRIGGER_NAME = "extConfMonitorTrigger";
    public static final int CONF_UPDATER_JOB_INTERVAL_SECONDS = 5;

    public static final String LOG_CLEANER_JOB_NAME = "oldLogFilesCleanerJob";
    public static final String LOG_CLEANER_JOB_TRIGGER_NAME = "oldLogFilesCleanerTrigger";
    public static final String LOG_CLEANER_JOB_CRON_EXP = "0 0 0 * * ?";

    public static final String JOB_GROUP_NAME = "jg-logging";

    public void start() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            scheduleConfUpdaterJob(scheduler);
            scheduleLogCleanerJob(scheduler);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void scheduleConfUpdaterJob(Scheduler scheduler) throws SchedulerException {
        JobDetail confUpdaterJob = newJob(LogConfigurationMonitor.class)
                .withIdentity(CONF_UPDATER_JOB_NAME, JOB_GROUP_NAME)
                .build();

        Trigger confUpdaterTrigger = newTrigger()
                .withIdentity(CONF_UPDATER_JOB_TRIGGER_NAME, JOB_GROUP_NAME)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(CONF_UPDATER_JOB_INTERVAL_SECONDS)
                        .repeatForever())
                .build();

        scheduler.scheduleJob(confUpdaterJob, confUpdaterTrigger);
    }

    private void scheduleLogCleanerJob(Scheduler scheduler) throws SchedulerException {
        JobDetail logCleanerJob = newJob(OldLogFilesCleaner.class)
                .withIdentity(LOG_CLEANER_JOB_NAME, JOB_GROUP_NAME)
                .build();

        Trigger logCleanerTrigger = newTrigger()
                .withIdentity(LOG_CLEANER_JOB_TRIGGER_NAME, JOB_GROUP_NAME)
                .startNow()
                .withSchedule(
                        cronSchedule(LOG_CLEANER_JOB_CRON_EXP))
                .build();

        scheduler.scheduleJob(logCleanerJob, logCleanerTrigger);
    }
}
