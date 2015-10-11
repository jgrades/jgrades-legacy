/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.admin.accounts.mass;

import com.google.common.collect.Sets;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.RowProcessor;
import org.jgrades.admin.api.model.StudentCsvEntry;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.joda.time.format.DateTimeFormat.forPattern;

@Component
public class CsvRowProcessor implements RowProcessor {
    @Value("${rest.csv.delimiter}")
    private char delimiter;

    @Value("${rest.csv.date.of.birth.format}")
    private String dateOfBirthFormat;

    private Set<StudentCsvEntry> result;

    @Override
    public void processStarted(ParsingContext context) {
        result = Sets.newHashSet();
    }

    @Override
    public void rowProcessed(String[] studentRow, ParsingContext context) {
        if (studentRow.length != 5) {
            throw new IllegalArgumentException("Student record should have 5 values, but has " +
                    studentRow.length + ". CSV line: '" + context.currentParsedContent() + "'");
        }

        try {
            StudentCsvEntry entry = new StudentCsvEntry();
            entry.setName(studentRow[0]);
            entry.setSurname(studentRow[1]);
            entry.setDateOfBirth(LocalDate.parse(studentRow[2], forPattern(dateOfBirthFormat)));
            entry.setNationalIdentificationNumber(studentRow[3]);
            entry.setAddress(studentRow[4]);

            result.add(entry);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid birth date: '" + studentRow[2] + "'. " +
                    "Should be in format " + dateOfBirthFormat, e);
        }
    }

    @Override
    public void processEnded(ParsingContext context) {//NOSONAR
    }

    public Set<StudentCsvEntry> getResult() {
        return result;
    }
}
