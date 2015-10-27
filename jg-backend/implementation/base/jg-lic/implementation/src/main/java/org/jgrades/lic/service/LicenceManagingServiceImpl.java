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

import com.google.common.collect.Lists;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.io.FileUtils;
import org.dozer.Mapper;
import org.jgrades.lic.api.crypto.decrypt.LicenceDecryptionService;
import org.jgrades.lic.api.crypto.exception.LicenceCryptographyException;
import org.jgrades.lic.api.exception.LicenceNotFoundException;
import org.jgrades.lic.api.exception.UnreliableLicenceException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.lic.dao.LicenceRepository;
import org.jgrades.lic.entities.LicenceEntity;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.SignatureException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.valid4j.Validation.otherwiseThrowing;
import static org.valid4j.Validation.validate;

@Service
public class LicenceManagingServiceImpl implements LicenceManagingService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(LicenceManagingServiceImpl.class);

    @Value("${lic.keystore.path}")
    private String keystorePath;

    @Value("${lic.sec.data.path}")
    private String secDataPath;

    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private LicenceDecryptionService licenceDecryptionService;

    @Autowired
    @Qualifier("licMapper")
    private Mapper mapper;

    @Override
    public Licence installLicence(String licencePath, String signaturePath) {
        LOGGER.debug("Starting installation of licence: {} with signature: {}", licencePath, signaturePath);
        Licence licence = null;
        try {
            licence = licenceDecryptionService.decrypt(keystorePath, secDataPath, licencePath);
            LOGGER.debug("Licence file decrypted correctly");
            boolean isValid = licenceDecryptionService.validSignature(keystorePath, secDataPath, licencePath, signaturePath);

            if (isValid) {
                LOGGER.debug("Signature matches to the licence file. Saving licence in system");
                LicenceEntity licenceEntity = mapper.map(licence, LicenceEntity.class);
                licenceEntity.setLicenceFilePath(licencePath);
                licenceEntity.setSignatureFilePath(signaturePath);
                licenceRepository.save(licenceEntity);
                LOGGER.debug("Licence saved in system");
            } else {
                LOGGER.debug("Signature doesn't match to the licence file");
                throw new SignatureException();
            }
        } catch (IOException | LicenceCryptographyException | SignatureException e) {
            LOGGER.error("Exception during attempt to install licence: {} with signature: {}", licencePath, signaturePath, e);
            throw new UnreliableLicenceException(e);
        }
        return licence;
    }

    @Override
    public void uninstallLicence(Licence licence) {
        LOGGER.debug("Starting to uninstall licence with uid {}", licence.getUid());
        LicenceEntity licenceEntity = licenceRepository.findOne(licence.getUid());
        if (Optional.ofNullable(licenceEntity).isPresent()) {
            boolean licenceFileDeleted = FileUtils.deleteQuietly(new File(licenceEntity.getLicenceFilePath()));
            LOGGER.debug("Licence file {} deleted: {}", licenceEntity.getLicenceFilePath(), licenceFileDeleted);
            boolean signatureFileDeleted = FileUtils.deleteQuietly(new File(licenceEntity.getSignatureFilePath()));
            LOGGER.debug("Signature file {} deleted: {}", licenceEntity.getSignatureFilePath(), signatureFileDeleted);
            licenceRepository.delete(licenceEntity);
            LOGGER.debug("Information about licence with uid {} removed from system", licence.getUid());
        } else {
            LOGGER.error("Licence with uid {} not found", licence.getUid());
            throw new LicenceNotFoundException("Licence with UID: " + licence.getUid() + " is not found in system");
        }
    }

    @Override
    public Licence get(Long uid) {
        LOGGER.debug("Getting licence with uid {}", uid);
        LicenceEntity licenceEntity = licenceRepository.findOne(uid);
        validate(licenceEntity, notNullValue(), otherwiseThrowing(LicenceNotFoundException.class));
        return mapper.map(licenceEntity, Licence.class);
    }

    @Override
    public List<Licence> getAll() {
        LOGGER.debug("Getting all licences in system");
        List<Licence> licences = Lists.newArrayList();
        List<LicenceEntity> entitiesList = IteratorUtils.toList(licenceRepository.findAll().iterator());
        for (LicenceEntity entity : entitiesList) {
            licences.add(mapper.map(entity, Licence.class));
        }
        LOGGER.trace("Returning all licences in system: {}", licences);
        return licences;
    }
}
