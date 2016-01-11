/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.backup.context;

import org.jgrades.security.utils.DecryptionProvider;
import org.jgrades.security.utils.EncryptionProvider;
import org.jgrades.security.utils.KeyStoreContentExtractor;
import org.jgrades.security.utils.SignatureProvider;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.listeners.JobChainingJobListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.File;
import java.io.IOException;

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
    @Value("${backup.keystore.path}")
    private String keystorePath;

    @Value("${backup.sec.data.path}")
    private String secDataPath;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "backupScheduler")
    public Scheduler backupScheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobChainingJobListener jobChain = new JobChainingJobListener("ChainingJobListener");
        jobChain.addJobChainLink(jobKey("StartBackupJob"), jobKey("ArchiveInternalFilesJob"));
        jobChain.addJobChainLink(jobKey("ArchiveInternalFilesJob"), jobKey("EncryptArchiveJob"));
        jobChain.addJobChainLink(jobKey("EncryptArchiveJob"), jobKey("DatabaseBackupJob"));
        jobChain.addJobChainLink(jobKey("DatabaseBackupJob"), jobKey("FinishBackupJob"));
        scheduler.getListenerManager().addJobListener(jobChain);
        scheduler.start();
        return scheduler;
    }

    @Bean
    public KeyStoreContentExtractor keyStoreContentExtractor() throws IOException {
        return new KeyStoreContentExtractor(new File(keystorePath), new File(secDataPath));
    }

    @Bean
    public EncryptionProvider encryptionProvider() throws IOException {
        return new EncryptionProvider(keyStoreContentExtractor());
    }

    @Bean
    public DecryptionProvider decryptionProvider() throws IOException {
        return new DecryptionProvider(keyStoreContentExtractor());
    }

    @Bean
    public SignatureProvider signatureProvider() throws IOException {
        return new SignatureProvider(keyStoreContentExtractor());
    }
}
