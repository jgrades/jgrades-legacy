/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.admin.accounts.mass;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.admin.api.model.StudentCsvEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.Set;

@Component
public class StudentDataCsvParser {
    @Value("${rest.csv.delimiter}")
    private char delimiter;

    @Autowired
    private CsvRowProcessor rowProcessor;

    public Set<StudentCsvEntry> parse(String studentCsvDataString) {
        CsvParser csvParser = new CsvParser(getCsvParserSettings());
        csvParser.parse(new StringReader(studentCsvDataString));
        return rowProcessor.getResult();
    }

    private CsvParserSettings getCsvParserSettings() {
        CsvParserSettings settings = new CsvParserSettings();

        settings.getFormat().setDelimiter(delimiter);
        settings.setRowProcessor(rowProcessor);
        settings.setLineSeparatorDetectionEnabled(true);
        settings.setNullValue(StringUtils.EMPTY);
        settings.setEmptyValue(StringUtils.EMPTY);

        return settings;
    }
}
