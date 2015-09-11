package org.jgrades.logging.utils;

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.model.LoggingConfiguration;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OldLogFileFilter implements FilenameFilter {
    private static final String SIMPLE_YYYY_MM_DD_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private static final String SIMPLE_YYYY_MM_DD_PATTERN = "yyyy-MM-dd";
    private static final Pattern DATE_PATTERN = Pattern.compile(SIMPLE_YYYY_MM_DD_REGEX);

    private LoggingConfigurationDao configurationDao = new LoggingConfigurationDaoFileImpl();

    @Override
    public boolean accept(File dir, String fileName) {
        LocalDate lastValidDate = getLastValidDate();
        LocalDate logFileDate = getLocalDate(fileName);
        return logFileDate.isBefore(lastValidDate);
    }

    private LocalDate getLastValidDate() {
        LoggingConfiguration config = configurationDao.getCurrentConfiguration();
        Integer maxDays = config.getMaxDays();

        LocalDate today = LocalDate.now();
        return today.minusDays(maxDays - 1);
    }

    private LocalDate getLocalDate(String fileName) {
        Matcher m = DATE_PATTERN.matcher(fileName);
        try {
            if (m.find()) {
                String dateFormat = m.group();
                return LocalDate.parse(dateFormat, DateTimeFormat.forPattern(SIMPLE_YYYY_MM_DD_PATTERN));
            }
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException ex) {
            return LocalDate.now();
        }
    }
}
