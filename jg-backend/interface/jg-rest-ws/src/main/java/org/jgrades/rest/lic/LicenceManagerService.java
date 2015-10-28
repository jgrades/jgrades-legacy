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

import org.apache.commons.io.FileUtils;
import org.jgrades.lic.api.exception.LicenceException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.api.lic.ILicenceManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/licence", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class LicenceManagerService implements ILicenceManagerService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(LicenceManagerService.class);

    @Autowired
    private LicenceManagingService licenceManagingService;

    @Autowired
    private IncomingFilesNameResolver filesNameResolver;

    private static void checkFilesExisting(MultipartFile licence, MultipartFile signature) {
        if (licence.isEmpty()) {
            LOGGER.warn("Licence file is empty");
            throw new IllegalArgumentException("Empty licence file");
        } else if (signature.isEmpty()) {
            LOGGER.warn("Signature file is empty");
            throw new IllegalArgumentException("Empty signature file");
        }
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<Licence> getAll() {
        return licenceManagingService.getAll();
    }

    @Override
    @RequestMapping(value = "/{uid}", method = RequestMethod.GET)
    public Licence get(@PathVariable Long uid) {
        LOGGER.info("Getting licence with uid {}", uid);
        return licenceManagingService.get(uid);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Licence uploadAndInstall(@RequestParam("licence") MultipartFile licence,
                                    @RequestParam("signature") MultipartFile signature) throws IOException {
        LOGGER.info("Licence installation service invoked");
        checkFilesExisting(licence, signature);

        filesNameResolver.init();
        File licenceFile = filesNameResolver.getLicenceFile();
        File signatureFile = filesNameResolver.getSignatureFile();

        String licenceFilePath = licenceFile.getAbsolutePath();
        String signatureFilePath = signatureFile.getAbsolutePath();

        LOGGER.info("Saving received licence file to {}", licenceFilePath);
        FileUtils.writeByteArrayToFile(licenceFile, licence.getBytes());

        LOGGER.info("Saving received signature file to {}", signatureFilePath);
        FileUtils.writeByteArrayToFile(signatureFile, signature.getBytes());

        try {
            return licenceManagingService.installLicence(licenceFilePath, signatureFilePath);
        } catch (LicenceException ex) {
            LOGGER.error("Problem during installation licence: {} and signature: {}", licenceFilePath, signatureFilePath, ex);
            LOGGER.error("Due to exception during installation saved file will be removed: {} , {}", licenceFilePath, signatureFilePath);
            FileUtils.deleteQuietly(licenceFile);
            FileUtils.deleteQuietly(signatureFile);
            throw ex;
        }
    }

    @Override
    @RequestMapping(value = "/{uid}", method = RequestMethod.DELETE)
    public void uninstall(@PathVariable Long uid) {
        LOGGER.info("Starting of removing a licence with uid {}", uid);
        Licence licenceToRemove = licenceManagingService.get(uid);
        licenceManagingService.uninstallLicence(licenceToRemove);
    }
}
