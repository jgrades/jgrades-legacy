/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.utils;

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.model.LoggingConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OldLogFileFilterTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    private LoggingConfigurationDao loggingConfigurationDao;

    @InjectMocks
    private OldLogFileFilter oldLogFileFilter;

    @Test
    public void shouldSelectFile_whenFileNameHasObsoleteDate() throws Exception {
        // given
        Integer maxDays = 7;
        LoggingConfiguration configuration = new LoggingConfiguration();
        configuration.setMaxDays(maxDays);
        when(loggingConfigurationDao.getCurrentConfiguration()).thenReturn(configuration);

        LocalDate actualDate = LocalDate.now();
        String okDate = actualDate.minusDays(maxDays - 1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String nokDate = actualDate.minusDays(maxDays).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String nokDate2 = actualDate.minusDays(maxDays + 1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String fileName1 = "jg_monitor_" + okDate + "_0.log";
        String fileName2 = "jg_monitor_" + nokDate + "_0.log";
        String fileName3 = "jg_monitor_" + nokDate2 + "_0.log";


        // when
        boolean isTakenToRemoving1 = oldLogFileFilter.accept(temporaryFolder.newFolder(), fileName1);
        boolean isTakenToRemoving2 = oldLogFileFilter.accept(temporaryFolder.newFolder(), fileName2);
        boolean isTakenToRemoving3 = oldLogFileFilter.accept(temporaryFolder.newFolder(), fileName3);

        // then
        assertThat(isTakenToRemoving1).isFalse();
        assertThat(isTakenToRemoving2).isTrue();
        assertThat(isTakenToRemoving3).isTrue();
    }

    @Test
    public void shouldIgnoreFile_whenFileNameHasNotDateInProperFormat() throws Exception {
        // given
        Integer maxDays = 7;
        LoggingConfiguration configuration = new LoggingConfiguration();
        configuration.setMaxDays(maxDays);
        when(loggingConfigurationDao.getCurrentConfiguration()).thenReturn(configuration);

        LocalDate actualDate = LocalDate.now();
        String wrongDateFormat = "dd.MM.yyyy";
        String nokDate = actualDate.minusDays(maxDays).format(DateTimeFormatter.ofPattern(wrongDateFormat));
        String fileName = "jg_monitor_" + nokDate + "_0.log";

        // when
        boolean isTakenToRemoving = oldLogFileFilter.accept(temporaryFolder.newFolder(), fileName);

        // then
        assertThat(isTakenToRemoving).isFalse();
    }
}
