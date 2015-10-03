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

import org.hamcrest.core.Is;
import org.jgrades.lic.api.exception.LicenceNotFoundException;
import org.jgrades.lic.api.model.Licence;
import org.jgrades.lic.api.model.LicenceValidationResult;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.property.ApplicationPropertiesConfig;
import org.jgrades.rest.RestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationPropertiesConfig.class, LicMockConfig.class, RestConfig.class})
@WebAppConfiguration
public class LicenceCheckServiceTest {
    private MockMvc mockMvc;

    @Autowired
    private LicenceCheckService licenceCheckService;

    @Autowired
    private LicenceManagingService licenceManagingServiceMock;

    @Autowired
    private LicenceCheckingService licenceCheckingServiceMock;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(licenceCheckService)
                .setHandlerExceptionResolvers(new RestConfig().restExceptionResolver())
                .build();
        Mockito.reset(licenceManagingServiceMock, licenceCheckingServiceMock);
    }

    @Test
    public void shouldConfirmValid_whenLicenceIsValid() throws Exception {
        // given
        Long uid = 1234L;
        Licence licence = new Licence();
        LicenceValidationResult resultOk = new LicenceValidationResult();

        when(licenceManagingServiceMock.get(eq(uid))).thenReturn(licence);
        when(licenceCheckingServiceMock.checkValid(eq(licence))).thenReturn(resultOk);

        // when
        mockMvc.perform(get("/licence/check/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid", Is.is(true)));

        // then
        verify(licenceManagingServiceMock, times(1)).get(uid);
        verify(licenceCheckingServiceMock, times(1)).checkValid(licence);
    }

    @Test
    public void shouldConfirmNotValid_whenLicenceIsNotValid() throws Exception {
        // given
        Long uid = 1234L;
        Licence licence = new Licence();
        LicenceValidationResult resultNok = new LicenceValidationResult(false, "error message");

        when(licenceManagingServiceMock.get(eq(uid))).thenReturn(licence);
        when(licenceCheckingServiceMock.checkValid(eq(licence))).thenReturn(resultNok);

        // when
        mockMvc.perform(get("/licence/check/{uid}", uid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid", Is.is(false)));

        // then
        verify(licenceManagingServiceMock, times(1)).get(uid);
        verify(licenceCheckingServiceMock, times(1)).checkValid(licence);
    }

    @Test
    public void shouldReturnLicenceNotFoundException_whenLicenceNotFound() throws Exception {
        // given
        when(licenceManagingServiceMock.get(anyLong())).thenReturn(null);
        when(licenceCheckingServiceMock.checkValid(null)).thenThrow(LicenceNotFoundException.class);

        // when then
        mockMvc.perform(get("/licence/check/{uid}", 1500100900L))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.title", is(LicenceNotFoundException.class.getSimpleName())));
    }

    @Test
    public void shouldConfirmValidForProduct_whenLicenceForProductIsValid() throws Exception {
        // given
        String productName = "JG-BASE";
        LicenceValidationResult resultOk = new LicenceValidationResult();
        when(licenceCheckingServiceMock.checkValidForProduct(productName)).thenReturn(resultOk);

        // when
        mockMvc.perform(get("/licence/check/product/{productName}", productName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid", Is.is(true)));

        // then
        verify(licenceCheckingServiceMock, times(1)).checkValidForProduct(productName);
    }

    @Test
    public void shouldConfirmNotValidForProduct_whenLicenceForProductIsNotValid() throws Exception {
        // given
        String productName = "JG-BASE";
        LicenceValidationResult resultNok = new LicenceValidationResult(false, "error message");
        when(licenceCheckingServiceMock.checkValidForProduct(productName)).thenReturn(resultNok);

        // when
        mockMvc.perform(get("/licence/check/product/{productName}", productName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid", Is.is(false)));

        // then
        verify(licenceCheckingServiceMock, times(1)).checkValidForProduct(productName);
    }
}
