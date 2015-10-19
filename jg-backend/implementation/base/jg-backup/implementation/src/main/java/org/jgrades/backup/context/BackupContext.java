/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.backup.context;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.listeners.JobChainingJobListener;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.quartz.JobKey.jobKey;

@Configuration
@EnableJpaRepositories(
        basePackages = {"org.jgrades.backup.api.dao"},
        entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager")
@PropertySources({
        @PropertySource("classpath:jg-backup.properties"),
        @PropertySource(value = "file:${jgrades.application.properties.file}", ignoreResourceNotFound = true)
})
@EnableTransactionManagement
@ComponentScan("org.jgrades.backup")
public class BackupContext {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "backupScheduler")
    public Scheduler backupScheduler() {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobChainingJobListener jobChain = new JobChainingJobListener("ChainingJobListener");
            jobChain.addJobChainLink(jobKey("StartBackupJob"), jobKey("ArchiveInternalFilesJob"));
            jobChain.addJobChainLink(jobKey("ArchiveInternalFilesJob"), jobKey("EncryptArchiveJob"));
            jobChain.addJobChainLink(jobKey("EncryptArchiveJob"), jobKey("DatabaseBackupJob"));
            jobChain.addJobChainLink(jobKey("DatabaseBackupJob"), jobKey("FinishBackupJob"));
            scheduler.getListenerManager().addJobListener(jobChain);
            scheduler.start();
            return scheduler;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
