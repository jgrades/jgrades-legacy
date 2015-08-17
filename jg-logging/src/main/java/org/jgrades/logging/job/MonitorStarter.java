package org.jgrades.logging.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class MonitorStarter {
    public void start() {
        try {
            SchedulerFactory schedFact = new StdSchedulerFactory();
            Scheduler sched = schedFact.getScheduler();
            sched.start();

            JobDetail job = newJob(LogConfigurationMonitor.class)
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
}
