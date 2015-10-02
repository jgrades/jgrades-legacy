/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.utils;

import org.dozer.DozerConverter;
import org.joda.time.DateTime;

public class DateTimeMappingConverter extends DozerConverter<DateTime, DateTime> {

    public DateTimeMappingConverter() {
        super(DateTime.class, DateTime.class);
    }

    @Override
    public DateTime convertTo(final DateTime source, final DateTime destination) {
        return source == null ? null : new DateTime(source);
    }

    @Override
    public DateTime convertFrom(final DateTime source, final DateTime destination) {
        return source == null ? null : new DateTime(source);
    }
}
