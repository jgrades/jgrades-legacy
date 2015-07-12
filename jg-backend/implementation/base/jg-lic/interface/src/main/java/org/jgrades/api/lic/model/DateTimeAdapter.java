package org.jgrades.api.lic.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Locale;

class DateTimeAdapter extends XmlAdapter<String, DateTime> {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(PATTERN);

    public DateTime unmarshal(String v) {
        dateTimeFormatter.withLocale(Locale.ENGLISH);
        return DateTime.parse(v, dateTimeFormatter);
    }

    public String marshal(DateTime v) {
        dateTimeFormatter.withLocale(Locale.ENGLISH);
        return v.toString(dateTimeFormatter);
    }
}
