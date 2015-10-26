/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.data;

import org.jgrades.data.api.model.DataSourceDetails;
import org.jgrades.rest.api.data.IDataSourceDetailsService;
import org.jgrades.rest.client.CoreRestClient;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DataSourceDetailsServiceClient extends CoreRestClient implements IDataSourceDetailsService {
    @Autowired
    public DataSourceDetailsServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                          StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, restTemplate);
    }

    @Override
    public DataSourceDetails getDataSourceDetails() {
        String serviceUrl = "/datasource";
        ResponseEntity<DataSourceDetails> response = restTemplate.exchange(backendBaseUrl + serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, DataSourceDetails.class);
        return response.getBody();
    }

    @Override
    public void setDataSourceDetails(DataSourceDetails details) {
        String serviceUrl = "/datasource";
        HttpEntity<DataSourceDetails> entity = new HttpEntity<>(details);
        restTemplate.exchange(backendBaseUrl + serviceUrl, HttpMethod.POST, entity, Void.class);
    }

    @Override
    public boolean testConnection() {
        String serviceUrl = "/datasource/test";
        ResponseEntity<Boolean> response = restTemplate.exchange(backendBaseUrl + serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, Boolean.class);
        return response.getBody();
    }
}
