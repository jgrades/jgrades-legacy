/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.aop;

import org.jgrades.lic.api.LicServiceMockConfig;
import org.jgrades.lic.api.context.LicApiContext;
import org.jgrades.lic.api.exception.LicenceNotFoundException;
import org.jgrades.lic.api.model.LicenceValidationResult;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {LicApiContext.class, LicServiceMockConfig.class})
public class LicenceAspectTest {
    private static final String BASE_PRODUCT_NAME = "JG-BASE";
    private static final String EXAMPLE_CUSTOM_PRODUCT_NAME = FakeService.CUSTOM_PRODUCT_NAME;

    @Autowired
    private LicenceCheckingService licenceService;

    @Autowired
    private FakeService fakeService;

    @Before
    public void setUp() throws Exception {
        reset(licenceService);
    }

    @Test
    public void shouldCheckLicenceForBase_whenMethodRequiredBaseLicenceActivated() throws Exception {
        // given
        when(licenceService.checkValidForProduct(BASE_PRODUCT_NAME))
                .thenReturn(new LicenceValidationResult());

        // when
        fakeService.operationRequiredBaseLicence();

        // then
        verify(licenceService, times(1)).checkValidForProduct(BASE_PRODUCT_NAME);
    }

    @Test
    public void shouldCheckLicenceForBase_forEachInvoking() throws Exception {
        // given
        when(licenceService.checkValidForProduct(BASE_PRODUCT_NAME))
                .thenReturn(new LicenceValidationResult());

        // when
        fakeService.operationRequiredBaseLicence();
        fakeService.operationRequiredBaseLicence();
        fakeService.operationRequiredBaseLicence();

        // then
        verify(licenceService, times(3)).checkValidForProduct(BASE_PRODUCT_NAME);
    }

    /*
        Explaination of weird result:
        http://stackoverflow.com/questions/13564627/spring-aop-not-working-for-method-call-inside-another-method
     */
    @Test
    public void shouldNotCheckLicence_whenInvokingMethodIsInsideAnother() throws Exception {
        // when
        fakeService.someInternalOperationNeedsBaseLicence();

        // then
        verifyNoMoreInteractions(licenceService);
    }

    @Test
    public void shouldCheckCustomProductLicence_whenMethodRequiredThisLicenceActivated() throws Exception {
        // given
        when(licenceService.checkValidForProduct(EXAMPLE_CUSTOM_PRODUCT_NAME))
                .thenReturn(new LicenceValidationResult());

        // when
        fakeService.operationRequiredCustomComponentName();

        // then
        verify(licenceService, times(1)).checkValidForProduct(EXAMPLE_CUSTOM_PRODUCT_NAME);
    }

    @Test
    public void shouldNotWorking_whenAspectMountedOnStaticMethod() throws Exception {
        // when
        FakeService.foooo();

        // then
        verifyZeroInteractions(licenceService);
    }

    @Test(expected = LicenceNotFoundException.class)
    public void shouldThrowException_whenLicenceServiceThrowException() throws Exception {
        // given
        when(licenceService.checkValidForProduct(BASE_PRODUCT_NAME))
                .thenReturn(new LicenceValidationResult(false, "Licence expired"));

        // when
        fakeService.operationRequiredBaseLicence();

        // then
        // should throw LicenceNotFoundException
    }
}
