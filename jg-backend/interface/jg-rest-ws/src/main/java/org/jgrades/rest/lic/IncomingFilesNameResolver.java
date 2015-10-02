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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class IncomingFilesNameResolver {
    @Value("${rest.lic.path}")
    private String licPath;

    @Value("${rest.lic.file.pattern}")
    private String fileNamePattern;

    @Value("${rest.lic.file.licence.extension}")
    private String licenceExtension;

    @Value("${rest.lic.file.signature.extension}")
    private String signatureExtension;

    private DateTimeFormatter formatter;
    private DateTime dateTime;

    @PostConstruct
    public void init() {
        formatter = DateTimeFormat.forPattern(fileNamePattern);
        dateTime = DateTime.now();
    }

    public File getLicenceFile() {
        return new File(licPath + File.separator + formatter.print(dateTime) + "." + licenceExtension);
    }

    public File getSignatureFile() {
        return new File(licPath + File.separator + formatter.print(dateTime) + "." + signatureExtension);
    }
}
