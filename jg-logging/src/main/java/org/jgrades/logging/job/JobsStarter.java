/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.job;

import com.google.common.collect.Sets;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
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
    public static final String LOG_CLEANER_START_JOB_TRIGGER_NAME = "oldLogFilesCleanerTrigger_OnStartUp";
    public static final String LOG_CLEANER_JOB_CRON_EXP = "0 0 0 * * ?";
    public static final String JOB_GROUP_NAME = "jg-logging";
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(JobsStarter.class);

    public void start() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            scheduleConfUpdaterJob(scheduler);
            scheduleLogCleanerJob(scheduler);
        } catch (SchedulerException e) {
            LOGGER.error("Problem during starting jobs for logging purposes", e);
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
                .withSchedule(cronSchedule(LOG_CLEANER_JOB_CRON_EXP))
                .build();

        Trigger logCleanerStartupTrigger = newTrigger()
                .withIdentity(LOG_CLEANER_START_JOB_TRIGGER_NAME, JOB_GROUP_NAME)
                .startNow()
                .build();

        scheduler.scheduleJob(logCleanerJob, Sets.newHashSet(logCleanerTrigger, logCleanerStartupTrigger), true);
    }
}
