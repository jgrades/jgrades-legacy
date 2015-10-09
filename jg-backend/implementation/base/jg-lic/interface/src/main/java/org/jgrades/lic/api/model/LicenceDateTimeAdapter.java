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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Locale;

public class LicenceDateTimeAdapter extends XmlAdapter<String, DateTime> {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(PATTERN);

    public static DateTimeFormatter getLicDateTimeFormatter() {
        return dateTimeFormatter;
    }

    @Override
    public DateTime unmarshal(String v) {
        dateTimeFormatter.withLocale(Locale.ENGLISH);
        return DateTime.parse(v, dateTimeFormatter);
    }

    @Override
    public String marshal(DateTime v) {
        dateTimeFormatter.withLocale(Locale.ENGLISH);
        return v.toString(dateTimeFormatter);
    }
}
