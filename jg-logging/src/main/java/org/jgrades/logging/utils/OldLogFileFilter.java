/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.utils;

import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.dao.LoggingConfigurationDaoFileImpl;
import org.jgrades.logging.model.LoggingConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OldLogFileFilter implements FilenameFilter {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(OldLogFileFilter.class);

    private static final String SIMPLE_YYYY_MM_DD_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private static final String SIMPLE_YYYY_MM_DD_PATTERN = "yyyy-MM-dd";
    private static final Pattern DATE_PATTERN = Pattern.compile(SIMPLE_YYYY_MM_DD_REGEX);

    private LoggingConfigurationDao configurationDao = new LoggingConfigurationDaoFileImpl();

    private static LocalDate getLocalDate(String fileName) {
        Matcher m = DATE_PATTERN.matcher(fileName);
        try {
            if (m.find()) {
                String dateFormat = m.group();
                return LocalDate.parse(dateFormat, DateTimeFormatter.ofPattern(SIMPLE_YYYY_MM_DD_PATTERN));
            }
            throw new IllegalArgumentException(fileName);
        } catch (IllegalArgumentException e) {
            LOGGER.debug("Ignoring file {} in log directory, because name doesn't contain date in format {}",
                    fileName, SIMPLE_YYYY_MM_DD_PATTERN, e);
            return LocalDate.now();
        }
    }

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
}
