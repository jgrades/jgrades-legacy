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
