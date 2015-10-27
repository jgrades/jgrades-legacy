/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.model;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenceDateTimeAdapterTest {
    @Test
    public void shouldReturnCorrectFormatter() throws Exception {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2015, 7, 19, 5, 59, 59);
        String expectedString = "2015-07-19 05:59:59";

        // when
        DateTimeFormatter formatter = LicenceDateTimeAdapter.getLicDateTimeFormatter();
        String dateTimeString = dateTime.format(formatter);

        // then
        assertThat(dateTimeString).isEqualTo(expectedString);
    }

    @Test
    public void shouldParseStringInCorrectFormat() throws Exception {
        // given
        String inputDateTimeString = "2015-07-19 05:59:59";
        LocalDateTime expectedDateTime = LocalDateTime.of(2015, 7, 19, 5, 59, 59);

        // when
        DateTimeFormatter formatter = LicenceDateTimeAdapter.getLicDateTimeFormatter();
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTimeString, formatter);

        // then
        assertThat(dateTime).isEqualTo(expectedDateTime);
    }
}
