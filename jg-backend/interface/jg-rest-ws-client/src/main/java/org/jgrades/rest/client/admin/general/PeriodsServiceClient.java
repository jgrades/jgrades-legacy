/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.admin.general;

import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.jgrades.rest.api.admin.general.IPeriodsService;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.jgrades.rest.client.common.RestCrudServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class PeriodsServiceClient extends RestCrudServiceClient<SchoolDayPeriod, Long> implements IPeriodsService {
    @Autowired
    public PeriodsServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, "/period", restTemplate);
    }

    @Override
    public void insertWithGenerator(PeriodsGeneratorSettings generationSettings) {
        String serviceUrl = backendBaseUrl + crudUrl + "/generator";
        HttpEntity<PeriodsGeneratorSettings> entity = new HttpEntity<>(generationSettings);
        restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, Void.class);
    }
}
