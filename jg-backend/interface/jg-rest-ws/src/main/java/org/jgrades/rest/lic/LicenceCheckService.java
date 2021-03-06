/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.lic;

import io.swagger.annotations.ApiOperation;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceValidationResult;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.api.lic.ILicenceCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/licence/check", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
@PreAuthorize("hasRole('ADMINISTRATOR')")
public class LicenceCheckService implements ILicenceCheckService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(LicenceCheckService.class);

    @Autowired
    private LicenceManagingService licenceManagingService;

    @Autowired
    private LicenceCheckingService licenceCheckingService;

    @Override
    @RequestMapping(value = "/{uid}", method = RequestMethod.GET)
    @ApiOperation(value = "Check is licence with given UID valid")
    public LicenceValidationResult check(@PathVariable Long uid) {
        LOGGER.info("Checking licence with uid {}", uid);
        Licence licence = licenceManagingService.get(uid);
        return licenceCheckingService.checkValid(licence);
    }

    @Override
    @RequestMapping(value = "/product/{productName}", method = RequestMethod.GET)
    public LicenceValidationResult checkForProduct(@PathVariable String productName) {
        LOGGER.info("Checking licence for product {}", productName);
        return licenceCheckingService.checkValidForProduct(productName);
    }
}
