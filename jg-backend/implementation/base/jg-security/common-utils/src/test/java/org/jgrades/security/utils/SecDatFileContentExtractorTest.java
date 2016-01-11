/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;

import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.assertj.core.api.Assertions.assertThat;

public class SecDatFileContentExtractorTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test(expected = FileNotFoundException.class)
    public void shouldThrow_whenFileDoNotExists() throws Exception {
        // given
        File notExistingFile = new File("UNKNOWN");

        // when
        new SecDatFileContentExtractor(notExistingFile);

        // then
        // should throw FileNotFoundException
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrow_whenFileIsEmpty() throws Exception {
        // given
        File emptyFile = folder.newFile();

        // when
        new SecDatFileContentExtractor(emptyFile);

        // then
        // should throw IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrow_whenFileHasLessThenThreeLines() throws Exception {
        // given
        File fileWithTwoLines = folder.newFile();
        writeStringToFile(fileWithTwoLines, "line1\n", true);
        writeStringToFile(fileWithTwoLines, "line2", true);

        // when
        new SecDatFileContentExtractor(fileWithTwoLines);

        // then
        // should throw IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrow_whenFileHasMoreThenThreeLines() throws Exception {
        // given
        File fileWithFourLines = folder.newFile();
        writeStringToFile(fileWithFourLines, "line1\n", true);
        writeStringToFile(fileWithFourLines, "line2\n", true);
        writeStringToFile(fileWithFourLines, "line3\n", true);
        writeStringToFile(fileWithFourLines, "line4", true);

        // when
        new SecDatFileContentExtractor(fileWithFourLines);

        // then
        // should throw IllegalArgumentException
    }

    @Test
    public void shouldParseSecDatFileCorrectly() throws Exception {
        // given
        File file = folder.newFile();
        String keystoreDat = "xxxx";
        String encryptionDat = "yyyy";
        String signatureDat = "zzzz";

        writeStringToFile(file, keystoreDat + "\n", true);
        writeStringToFile(file, encryptionDat + "\n", true);
        writeStringToFile(file, signatureDat, true);

        // when
        SecDatFileContentExtractor extractor = new SecDatFileContentExtractor(file);

        // then
        assertThat(extractor.getKeystoreDat()).isEqualTo(keystoreDat.toCharArray());
        assertThat(extractor.getEncryptionDat()).isEqualTo(encryptionDat.toCharArray());
        assertThat(extractor.getSignatureDat()).isEqualTo(signatureDat.toCharArray());
    }

    @Test
    public void shouldParseAndIgnoreLastEmptyLine() throws Exception {
        // given
        File file = folder.newFile();
        String keystoreDat = "xxxx";
        String encryptionDat = "yyyy";
        String signatureDat = "zzzz";

        writeStringToFile(file, keystoreDat + "\n", true);
        writeStringToFile(file, encryptionDat + "\n", true);
        writeStringToFile(file, signatureDat + "\n", true);

        // when
        SecDatFileContentExtractor extractor = new SecDatFileContentExtractor(file);

        // then
        assertThat(extractor.getKeystoreDat()).isEqualTo(keystoreDat.toCharArray());
        assertThat(extractor.getEncryptionDat()).isEqualTo(encryptionDat.toCharArray());
        assertThat(extractor.getSignatureDat()).isEqualTo(signatureDat.toCharArray());
    }
}
