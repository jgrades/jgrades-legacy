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

import org.jgrades.rest.api.admin.general.IWorkingDaysService;
import org.jgrades.rest.client.CoreRestClient;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Set;

@Component
public class WorkingDaysServiceClient extends CoreRestClient implements IWorkingDaysService {
    @Autowired
    public WorkingDaysServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                    StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, restTemplate);
    }

    @Override
    public Set<DayOfWeek> getWorkingDays() {
        String serviceUrl = backendBaseUrl + "/workingdays";
        ResponseEntity<Set<DayOfWeek>> response = restTemplate.exchange(serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Set<DayOfWeek>>() {
        });
        return response.getBody();
    }

    @Override
    public void setWorkingDays(Set<DayOfWeek> days) {
        String serviceUrl = backendBaseUrl + "/workingdays";
        HttpEntity<Set<DayOfWeek>> entity = new HttpEntity<>(days);
        restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, Void.class);

    }
}
