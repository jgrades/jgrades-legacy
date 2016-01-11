/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.admin.general;

import org.jgrades.data.api.entities.School;
import org.jgrades.rest.api.admin.general.ISchoolService;
import org.jgrades.rest.client.CoreRestClient;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SchoolServiceClient extends CoreRestClient implements ISchoolService {
    @Autowired
    public SchoolServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                               StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, restTemplate);
    }

    @Override
    public School getGeneralData() {
        String serviceUrl = backendBaseUrl + "/general";
        ResponseEntity<School> response = restTemplate.exchange(serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, School.class);
        return response.getBody();
    }

    @Override
    public void insertOrUpdate(School generalData) {
        String serviceUrl = backendBaseUrl + "/general";
        HttpEntity<School> entity = new HttpEntity<>(generalData);
        restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, Void.class);
    }
}
