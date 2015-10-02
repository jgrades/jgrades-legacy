/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SecDatFileContentExtractor {
    private char[] keystoreDat;
    private char[] encryptionDat;
    private char[] signatureDat;

    public SecDatFileContentExtractor(File secDatFile) throws IOException {
        parse(secDatFile);
    }

    private void parse(File secDatFile) throws IOException {
        List<String> data = FileUtils.readLines(secDatFile);
        if (data.size() != 3) {
            throw new IllegalArgumentException();
        }
        keystoreDat = data.get(0).toCharArray();
        encryptionDat = data.get(1).toCharArray();
        signatureDat = data.get(2).toCharArray();
    }

    public char[] getKeystoreDat() {
        return keystoreDat;
    }

    public char[] getEncryptionDat() {
        return encryptionDat;
    }

    public char[] getSignatureDat() {
        return signatureDat;
    }
}
