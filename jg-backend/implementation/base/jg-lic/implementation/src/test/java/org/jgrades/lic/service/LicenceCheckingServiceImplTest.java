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

import org.jgrades.lic.BaseTest;
import org.jgrades.lic.api.exception.LicenceNotFoundException;
import org.jgrades.lic.api.model.Customer;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceProperty;
import org.jgrades.lic.api.model.Product;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.dao.LicenceRepository;
import org.jgrades.lic.entities.LicenceEntity;
import org.jgrades.lic.entities.ProductEntity;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenceCheckingServiceImplTest extends BaseTest {
    @Value("${lic.product.release.version}")
    private String currentVersion;

    @Autowired
    private LicenceCheckingService checkingService;

    @Autowired
    private LicenceRepository licenceRepository;

    @Test
    public void shouldValid_whenAllRulesAreSatisfied() throws Exception {
        // given
        Licence licence = getValidLicenceWithoutProperties();

        // when
        boolean isValid = checkingService.checkValid(licence);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldNotValid_whenDateRuleIsNotSatisfied() throws Exception {
        // given
        Licence licence = getExpiredLicenceWithoutProperties();

        // when
        boolean isValid = checkingService.checkValid(licence);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotValid_whenMacPropertyIsPresent_andMacRuleIsNotSatisfied() throws Exception {
        // given
        Licence licence = getValidLicenceWithStrangeMac();

        // when
        boolean isValid = checkingService.checkValid(licence);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotValid_whenVersionRuleIsNotSatisfied() throws Exception {
        // given
        Licence licence = getValidLicenceWithAnotherVersion();

        // when
        boolean isValid = checkingService.checkValid(licence);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldValidForProduct_whenThereIsOneValidLicenceForGivenProduct() throws Exception {
        // given
        String productName = "JG-PLANNER";
        licenceRepository.save(getLicenceEntity(productName, currentVersion, true));

        // when
        boolean isValid = checkingService.checkValidForProduct(productName);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldValidForProduct_whenThereIsOneValidLicenceAndOneInvalidLicenceForGivenProduct() throws Exception {
        // given
        String productName = "JG-PLANNER";
        licenceRepository.save(getLicenceEntity(productName, currentVersion, false));
        licenceRepository.save(getLicenceEntity(productName, currentVersion, true));

        // when
        boolean isValid = checkingService.checkValidForProduct(productName);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldNotValidForProduct_whenThereIsNoLicenceForGivenProduct() throws Exception {
        // given
        String productName = "JG-PLANNER";

        // when
        boolean isValid = checkingService.checkValidForProduct(productName);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotValidForProduct_whenThereIsNoValidLicenceForGivenProduct() throws Exception {
        // given
        String productName = "JG-PLANNER";
        licenceRepository.save(getLicenceEntity(productName, currentVersion, true));
        licenceRepository.save(getLicenceEntity(productName, currentVersion, false));

        // when
        boolean isValid = checkingService.checkValidForProduct("JG-BASE");

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldNotValidForProduct_whenThereIsNoValidLicenceForGivenProductInCurrentVersion() throws Exception {
        // given
        String productName = "JG-PLANNER";
        licenceRepository.save(getLicenceEntity(productName, "1.0VERSION", true));
        licenceRepository.save(getLicenceEntity(productName, currentVersion, false));

        // when
        boolean isValid = checkingService.checkValidForProduct(productName);

        // then
        assertThat(isValid).isFalse();
    }

    @Test(expected = LicenceNotFoundException.class)
    public void shouldThrowException_whenNullLicenceHandled() throws Exception {
        // when
        checkingService.checkValid(null);
    }

    @After
    public void tearDown() throws Exception {
        licenceRepository.deleteAll();
    }

    private Licence getValidLicenceWithStrangeMac() {
        Licence licence = getBaseLicence();
        licence.getProduct().setValidFrom(DateTime.now().minusMonths(1));
        licence.getProduct().setValidTo(DateTime.now().plusMonths(1));
        licence.getProduct().setVersion(currentVersion);

        LicenceProperty property = new LicenceProperty();
        property.setName("mac");
        property.setValue("00:00:22:00:00:00");
        licence.getProperties().add(property);

        return licence;
    }

    private Licence getValidLicenceWithAnotherVersion() {
        Licence licence = getBaseLicence();
        licence.getProduct().setValidFrom(DateTime.now().minusMonths(1));
        licence.getProduct().setValidTo(DateTime.now().plusMonths(1));
        licence.getProduct().setVersion("CUSTOM VERSION 1.0");
        return licence;
    }

    private Licence getExpiredLicenceWithoutProperties() {
        Licence licence = getBaseLicence();
        licence.getProduct().setValidFrom(DateTime.now().minusMonths(2));
        licence.getProduct().setValidTo(DateTime.now().minusMonths(1));
        licence.getProduct().setVersion(currentVersion);
        return licence;
    }

    private Licence getValidLicenceWithoutProperties() {
        Licence licence = getBaseLicence();
        licence.getProduct().setValidFrom(DateTime.now().minusMonths(1));
        licence.getProduct().setValidTo(DateTime.now().plusMonths(1));
        licence.getProduct().setVersion(currentVersion);
        return licence;
    }

    private Licence getBaseLicence() {
        Licence licence = new Licence();
        licence.setUid(585L);

        Customer customer = new Customer();
        customer.setId(34L);
        customer.setName("High school");
        customer.setAddress("California");
        customer.setPhone("+1 555 555 555");
        licence.setCustomer(customer);

        Product product = new Product();
        product.setName("JG-BASE");
        licence.setProduct(product);

        return licence;
    }

    private LicenceEntity getLicenceEntity(String productName, String version, boolean valid) {
        LicenceEntity licenceEntity = new LicenceEntity();
        licenceEntity.setUid(new Random().nextLong());

        ProductEntity product = new ProductEntity();
        product.setName(productName);
        product.setVersion(version);
        if (valid) {
            product.setValidFrom(DateTime.now().minusDays(1));
            product.setValidTo(DateTime.now().plusDays(1));
        } else {
            product.setValidFrom(DateTime.now().minusDays(3));
            product.setValidTo(DateTime.now().minusDays(1));
        }
        licenceEntity.setProduct(product);
        return licenceEntity;
    }
}
