/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.security;

import org.apache.commons.lang.math.RandomUtils;
import org.jgrades.logging.model.JgLogLevel;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.model.LoggingStrategy;
import org.jgrades.rest.client.BaseTest;
import org.jgrades.rest.client.logging.LoggerConfigServiceClient;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class LoginServiceClientTest extends BaseTest {
    @Autowired
    private LoggerConfigServiceClient loggerConfigServiceClient;

    @Test
    public void shouldReturnDefaultConfig() throws Exception {
        // when
        LoggingConfiguration configuration = loggerConfigServiceClient.getConfiguration();

        // then
        assertThat(configuration).isNotNull();
    }

    @Test
    public void shouldReturnCurrentConfig() throws Exception {
        // when
        LoggingConfiguration configuration = loggerConfigServiceClient.getConfiguration();

        // then
        assertThat(configuration).isNotNull();
    }

    @Test
    public void shouldSetNewConfiguration() throws Exception {
        // given
        LoggingConfiguration configuration = new LoggingConfiguration(
                LoggingStrategy.LOG_FILE_PER_MODULE_AND_LEVEL,
                JgLogLevel.DEBUG,
                "50 MB",
                RandomUtils.nextInt(50));

        // when
        loggerConfigServiceClient.setNewConfiguration(configuration);
        LoggingConfiguration newConfig = loggerConfigServiceClient.getConfiguration();

        // then
        assertThat(newConfig).isEqualTo(configuration);
    }
}
