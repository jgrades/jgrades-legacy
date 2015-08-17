package org.jgrades.logging.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class MonitorStarter {
    public static final String JOB_NAME = "extConfMonitorJob";
    public static final String JOB_TRIGGER_NAME = "extConfMonitorTrigger";
    public static final String JOB_GROUP_NAME = "jg-logging";
    public static final int JOB_INTERVAL_SECONDS = 5;

    public void start() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            JobDetail job = newJob(LogConfigurationMonitor.class)
                    .withIdentity(JOB_NAME, JOB_GROUP_NAME)
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity(JOB_TRIGGER_NAME, JOB_GROUP_NAME)
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(JOB_INTERVAL_SECONDS)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
