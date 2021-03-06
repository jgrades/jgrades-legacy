/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.service;

import com.google.common.collect.ImmutableList;
import org.dozer.Mapper;
import org.jgrades.lic.api.exception.LicenceNotFoundException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceValidationResult;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.dao.LicenceRepository;
import org.jgrades.lic.entities.LicenceEntity;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.valid4j.Validation.otherwiseThrowing;
import static org.valid4j.Validation.validate;

@Service
public class LicenceCheckingServiceImpl implements LicenceCheckingService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(LicenceCheckingServiceImpl.class);

    @Value("${lic.disabled.mode}")
    private boolean licensingDisabledMode;

    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    @Qualifier("licMapper")
    private Mapper mapper;

    @Autowired
    private VersionRule versionRule;

    private List<ValidationRule> rules;

    @PostConstruct
    public void initIt() {
        rules = ImmutableList.of(new DateRule(), new MacRule(), versionRule);
    }

    @Override
    public LicenceValidationResult checkValid(Licence licence) {
        LOGGER.debug("Start checking validation of licence {}", licence);
        if (licensingDisabledMode) {
            return new LicenceValidationResult();
        }
        validate(licence, notNullValue(), otherwiseThrowing(LicenceNotFoundException.class));
        for (ValidationRule rule : rules) {
            LOGGER.debug("Start checking rule of validation: {} for licence with uid {}", rule.getClass().getName(), licence.getUid());
            if (!rule.isValid(licence)) {
                LOGGER.debug("Licence {} is not pass validation process with rule: {}", licence.getUid(), rule.getClass().getName());
                return new LicenceValidationResult(false, "Licence is not pass validation process with rule: " + rule.getClass().getName());
            }
        }
        LOGGER.debug("Licence with uid {} is valid", licence.getUid());
        return new LicenceValidationResult();
    }

    @Override
    public LicenceValidationResult checkValidForProduct(String productName) {
        LOGGER.debug("Start checking licences for product {}", productName);
        if (licensingDisabledMode) {
            return new LicenceValidationResult();
        }

        List<LicenceEntity> licences = licenceRepository.findByProductName(productName);

        for (LicenceEntity licenceEntity : licences) {
            LOGGER.debug("Licence with uid {} is for product {}", licenceEntity.getUid(), productName);
            Licence licence = mapper.map(licenceEntity, Licence.class);
            LicenceValidationResult validationResult = checkValid(licence);
            if (validationResult.isValid()) {
                LOGGER.debug("Valid licence for product {} found", productName);
                return validationResult;
            }
        }
        LOGGER.debug("Licences for product {} not found or all are not valid", productName);
        return new LicenceValidationResult(false, "Licence for product " + productName + " not found");
    }
}
