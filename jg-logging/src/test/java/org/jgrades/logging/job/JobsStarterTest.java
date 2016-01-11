/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.job;

import com.google.common.collect.Iterables;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class JobsStarterTest {
    @Test
    public void afterInvokingMethod_scheduleIsStarted() throws Exception {
        // given
        JobsStarter jobsStarter = new JobsStarter();

        // when
        jobsStarter.start();

        // then
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Collection<Scheduler> schedulers = schedulerFactory.getAllSchedulers();

        assertThat(schedulers).hasSize(1);
        Scheduler scheduler = Iterables.getFirst(schedulers, null);

        assertThat(scheduler.getTriggerGroupNames()).contains(JobsStarter.JOB_GROUP_NAME);
    }
}
