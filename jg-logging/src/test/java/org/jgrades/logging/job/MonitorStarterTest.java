package org.jgrades.logging.job;

import com.google.common.collect.Iterables;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class MonitorStarterTest {
    @Test
    public void afterInvokingMethod_scheduleIsStarted() throws Exception {
        // given
        MonitorStarter monitorStarter = new MonitorStarter();

        // when
        monitorStarter.start();

        // then
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Collection<Scheduler> schedulers = schedulerFactory.getAllSchedulers();

        assertThat(schedulers).hasSize(1);
        Scheduler scheduler = Iterables.getFirst(schedulers, null);

        assertThat(scheduler.getTriggerGroupNames()).contains(MonitorStarter.JOB_GROUP_NAME);
    }
}
