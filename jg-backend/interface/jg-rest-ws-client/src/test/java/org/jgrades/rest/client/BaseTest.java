/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client;

import org.apache.commons.io.IOUtils;
import org.jgrades.rest.client.context.RestClientContext;
import org.jgrades.rest.client.lic.LicenceCheckServiceClient;
import org.jgrades.rest.client.lic.LicenceManagerServiceClient;
import org.jgrades.rest.client.security.LoginServiceClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestClientContext.class, ContainerEnvSimulatorConfig.class})
@WebAppConfiguration
public abstract class BaseTest {
    @Autowired
    private LoginServiceClient loginServiceClient;

    @Autowired
    private LicenceManagerServiceClient licenceManagerServiceClient;

    @Autowired
    private LicenceCheckServiceClient licenceCheckServiceClient;

    @Before
    public void setUp() throws Exception {
        loginAsAdmin();
        installTestbedBaseLicence();
    }

    public void loginAsAdmin() {
        loginServiceClient.logIn("admin", "admin");
    }

    public void logout() {
        loginServiceClient.logOut();
    }

    public void installTestbedBaseLicence() throws Exception {
        if (!licenceCheckServiceClient.checkForProduct("JG-BASE").isValid()) {
            File licenceResource = new File("src/test/resources/0.4-DEV-SNAPSHOT.lic");
            FileInputStream input = new FileInputStream(licenceResource);
            MultipartFile licenceMultipartFile = new MockMultipartFile("licence",
                    licenceResource.getName(), "text/plain", IOUtils.toByteArray(input));

            File signatureResource = new File("src/test/resources/0.4-DEV-SNAPSHOT.lic.sign");
            FileInputStream input2 = new FileInputStream(signatureResource);
            MultipartFile signatureMultipartFile = new MockMultipartFile("signature",
                    licenceResource.getName(), "text/plain", IOUtils.toByteArray(input2));

            licenceManagerServiceClient.uploadAndInstall(licenceMultipartFile, signatureMultipartFile);
        }
    }
}
