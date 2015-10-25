/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.logging;

import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.rest.api.logging.ILoggerConfigService;
import org.jgrades.rest.client.CoreRestClient;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoggerConfigServiceClient extends CoreRestClient implements ILoggerConfigService {
    @Autowired
    public LoggerConfigServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                     StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, restTemplate);
    }

    @Override
    public LoggingConfiguration getDefaultConfiguration() {
        String serviceUrl = "/logging/configuration/default";
        ResponseEntity<LoggingConfiguration> response = restTemplate.exchange(backendBaseUrl + serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, LoggingConfiguration.class);
        return response.getBody();
    }

    @Override
    public LoggingConfiguration getConfiguration() {
        String serviceUrl = "/logging/configuration";
        ResponseEntity<LoggingConfiguration> response = restTemplate.exchange(backendBaseUrl + serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, LoggingConfiguration.class);
        return response.getBody();
    }

    @Override
    public void setNewConfiguration(LoggingConfiguration configuration) {
        String serviceUrl = "/logging/configuration";
        HttpEntity<LoggingConfiguration> entity = new HttpEntity<>(configuration);
        restTemplate.exchange(backendBaseUrl + serviceUrl, HttpMethod.POST, entity, Void.class);
    }
}
