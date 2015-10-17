/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.monitor.context;

import org.jgrades.monitor.api.context.MonitorApiContext;
import org.jgrades.monitor.dependency.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({
        @PropertySource("classpath:jg-monitor.properties"),
        @PropertySource(value = "classpath:jg-data.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "file:${jgrades.application.properties.file}", ignoreResourceNotFound = true)
})
@Import(MonitorApiContext.class)
@ComponentScan("org.jgrades.monitor")
public class MonitorContext {
    @Value("${data.db.jdbc.url}")
    private String jdbcUrl;

    @Value("${data.db.username}")
    private String username;

    @Value("${data.db.password}")
    private String password;

    @Value("${data.jdbc.driver}")
    private String jdbcDriver;

    @Value("${lic.keystore.path}")
    private String licKeystorePath;

    @Value("${lic.sec.data.path}")
    private String licSecDataPath;

    @Value("${monitor.logging.xml.file.location}")
    private String loggingXmlFilePath;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "mainDataSourceChecker")
    public DependencyChecker mainDataSourceChecker() {
        DataSourceDTO dsDTO = new DataSourceDTO();
        dsDTO.setUrl("jdbc:postgresql://" + jdbcUrl);
        dsDTO.setUsername(username);
        dsDTO.setPassword(password);
        return new DataSourceChecker(dsDTO, jdbcDriver);
    }

    @Bean(name = "licKeystoreChecker")
    public DependencyChecker licKeystoreChecker() {
        return new FileChecker(licKeystorePath);
    }

    @Bean(name = "licSecDataChecker")
    public DependencyChecker licSecDataChecker() {
        return new FileChecker(licSecDataPath);
    }

    @Bean(name = "logbackXmlChecker")
    public DependencyChecker logbackXmlChecker() {
        return new FileChecker(loggingXmlFilePath);
    }

    @Bean(name = "dummyChecker")
    public DependencyChecker dummyChecker() {
        return new DummyChecker();
    }


}
