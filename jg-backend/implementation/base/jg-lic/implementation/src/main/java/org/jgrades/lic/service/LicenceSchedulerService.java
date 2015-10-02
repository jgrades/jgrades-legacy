/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.service;

import org.dozer.Mapper;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.dao.LicenceRepository;
import org.jgrades.lic.entities.LicenceEntity;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LicenceSchedulerService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(LicenceSchedulerService.class);

    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private LicenceCheckingService checkingService;

    @Autowired
    private Mapper mapper;

    @Scheduled(cron = "${lic.checker.scheduler.cron}")
    public void monitorValidOfAllLicences() {
        LOGGER.debug("Start scheduled validation of all licences in system");
        Iterable<LicenceEntity> licences = licenceRepository.findAll();
        licences.forEach(licenceEntity -> {
            if (checkingService.checkValid(mapper.map(licenceEntity, Licence.class))) {
                LOGGER.debug("Licence with uid {} for product {} is valid", licenceEntity.getUid(), licenceEntity.getProduct().getName());
            } else {
                LOGGER.error("Licence with uid {} for product {} is NOT valid", licenceEntity.getUid(), licenceEntity.getProduct().getName());
            }
        });
        LOGGER.debug("End of scheduled validation of all licences in system");
    }
}
