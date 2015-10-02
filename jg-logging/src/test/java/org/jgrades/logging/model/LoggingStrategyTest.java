/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.model;

import org.jgrades.logging.model.updater.PerLevelUpdater;
import org.jgrades.logging.model.updater.PerModuleAndLevelUpdater;
import org.jgrades.logging.model.updater.PerModuleUpdater;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggingStrategyTest {
    @Test
    public void updaterForPerLevelStrategyShouldBelongToTheAppropriateUpdaterClass() throws Exception {
        // given
        LoggingStrategy strategy = LoggingStrategy.LOG_FILE_PER_LEVEL;

        // when
        XmlFileNameTagsUpdater updater = strategy.getUpdater();

        // then
        assertThat(updater).isInstanceOf(PerLevelUpdater.class);
    }

    @Test
    public void updaterForPerModuleStrategyShouldBelongToTheAppropriateUpdaterClass() throws Exception {
        // given
        LoggingStrategy strategy = LoggingStrategy.LOG_FILE_PER_MODULE;

        // when
        XmlFileNameTagsUpdater updater = strategy.getUpdater();

        // then
        assertThat(updater).isInstanceOf(PerModuleUpdater.class);
    }

    @Test
    public void updaterForPerModuleAndLevelStrategyShouldBelongToTheAppropriateUpdaterClass() throws Exception {
        // given
        LoggingStrategy strategy = LoggingStrategy.LOG_FILE_PER_MODULE_AND_LEVEL;

        // when
        XmlFileNameTagsUpdater updater = strategy.getUpdater();

        // then
        assertThat(updater).isInstanceOf(PerModuleAndLevelUpdater.class);
    }
}
