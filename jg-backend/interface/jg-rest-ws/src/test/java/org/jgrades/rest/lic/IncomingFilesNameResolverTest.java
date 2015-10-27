/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.lic;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class IncomingFilesNameResolverTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private IncomingFilesNameResolver incomingFilesNameResolver;

    @Before
    public void setUp() throws Exception {
        incomingFilesNameResolver = new IncomingFilesNameResolver();
    }

    @Test
    public void shouldCreateCorrectFiles_forGivenPropertiesInjected() throws Exception {
        // given
        File licFile = tempFolder.newFile();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd.HH-mm-ss");

        setField(incomingFilesNameResolver, "licPath", licFile.getParent());
        setField(incomingFilesNameResolver, "fileNamePattern", "yyy-MM-dd.HH-mm-ss");
        setField(incomingFilesNameResolver, "licenceExtension", "lic");
        setField(incomingFilesNameResolver, "signatureExtension", "lic.sign");

        // when
        incomingFilesNameResolver.init();
        LocalDateTime dateTime = (LocalDateTime) getField(incomingFilesNameResolver, "dateTime");

        File licenceFile = incomingFilesNameResolver.getLicenceFile();
        File signatureFile = incomingFilesNameResolver.getSignatureFile();

        // then
        assertThat(licenceFile.getParent()).isEqualTo(licFile.getParent());
        assertThat(licenceFile.getName()).isEqualTo(formatter.format(dateTime) + "." + "lic");

        assertThat(signatureFile.getParent()).isEqualTo(licFile.getParent());
        assertThat(signatureFile.getName()).isEqualTo(formatter.format(dateTime) + "." + "lic.sign");

    }
}
