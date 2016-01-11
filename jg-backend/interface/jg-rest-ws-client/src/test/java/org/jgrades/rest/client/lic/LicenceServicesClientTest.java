/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.lic;

import org.apache.commons.io.IOUtils;
import org.jgrades.rest.client.BaseTest;
import org.jgrades.rest.client.security.LoginServiceClient;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@Ignore
public class LicenceServicesClientTest extends BaseTest {
    @Autowired
    private LoginServiceClient loginServiceClient;

    @Autowired
    private LicenceManagerServiceClient licenceManagerServiceClient;

    @Autowired
    private LicenceCheckServiceClient licenceCheckServiceClient;

    @Test
    public void shouldInstall() throws Exception {
        loginServiceClient.logIn("admin", "admin");

        File licenceResource = new File("src/test/resources/0.4-DEV-SNAPSHOT.lic");
        FileInputStream input = new FileInputStream(licenceResource);
        MultipartFile licenceMultipartFile = new MockMultipartFile("licence",
                licenceResource.getName(), "text/plain", IOUtils.toByteArray(input));

        File signatureResource = new File("src/test/resources/0.4-DEV-SNAPSHOT.lic.sign");
        FileInputStream input2 = new FileInputStream(signatureResource);
        MultipartFile signatureMultipartFile = new MockMultipartFile("signature",
                licenceResource.getName(), "text/plain", IOUtils.toByteArray(input2));

        licenceManagerServiceClient.uploadAndInstall(licenceMultipartFile, signatureMultipartFile);

        System.out.println(licenceManagerServiceClient.getAll());
        System.out.println(licenceManagerServiceClient.get(1992L));
        System.out.println(licenceCheckServiceClient.check(1992L).isValid());
        System.out.println(licenceCheckServiceClient.checkForProduct("JG-BASE").isValid());

        licenceManagerServiceClient.uninstall(1992L);
        System.out.println(licenceManagerServiceClient.getAll());

        System.out.println(licenceCheckServiceClient.checkForProduct("JG-BASE").isValid());

    }
}
