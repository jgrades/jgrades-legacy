/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.model;


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LicenceDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public static DateTimeFormatter getLicDateTimeFormatter() {
        return DATE_TIME_FORMATTER;
    }

    @Override
    public LocalDateTime unmarshal(String v) {
        DATE_TIME_FORMATTER.withLocale(Locale.ENGLISH);
        return LocalDateTime.parse(v, DATE_TIME_FORMATTER);
    }

    @Override
    public String marshal(LocalDateTime v) {
        DATE_TIME_FORMATTER.withLocale(Locale.ENGLISH);
        return v.format(DATE_TIME_FORMATTER);
    }
}
