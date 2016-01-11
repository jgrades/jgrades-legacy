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

import org.jgrades.lic.api.model.LicenceValidationResult;
import org.jgrades.rest.api.lic.ILicenceCheckService;
import org.jgrades.rest.client.CoreRestClient;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LicenceCheckServiceClient extends CoreRestClient
        implements ILicenceCheckService {
    @Autowired
    public LicenceCheckServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                     StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, restTemplate);
    }

    @Override
    public LicenceValidationResult check(Long uid) {
        String serviceUrl = backendBaseUrl + "/licence/check/" + uid;
        ResponseEntity<LicenceValidationResult> response = restTemplate.exchange(serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, LicenceValidationResult.class);
        return response.getBody();
    }

    @Override
    public LicenceValidationResult checkForProduct(String productName) {
        String serviceUrl = backendBaseUrl + "/licence/check/product/" + productName;
        ResponseEntity<LicenceValidationResult> response = restTemplate.exchange(serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, LicenceValidationResult.class);
        return response.getBody();
    }
}
