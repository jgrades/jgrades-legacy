package org.jgrades.lic.api.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Locale;

public class LicenceDateTimeAdapter extends XmlAdapter<String, DateTime> {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(PATTERN);

    public static DateTimeFormatter getLicDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public DateTime unmarshal(String v) {
        dateTimeFormatter.withLocale(Locale.ENGLISH);
        return DateTime.parse(v, dateTimeFormatter);
    }

    public String marshal(DateTime v) {
        dateTimeFormatter.withLocale(Locale.ENGLISH);
        return v.toString(dateTimeFormatter);
    }
}
