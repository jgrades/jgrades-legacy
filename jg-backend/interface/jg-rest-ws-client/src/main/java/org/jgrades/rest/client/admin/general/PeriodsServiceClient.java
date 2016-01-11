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

import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.jgrades.rest.api.admin.general.IPeriodsService;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.jgrades.rest.client.common.RestCrudServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class PeriodsServiceClient extends RestCrudServiceClient<SchoolDayPeriod, Long> implements IPeriodsService {
    @Autowired
    public PeriodsServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, "/period", restTemplate);
    }

    @Override
    public SchoolDayPeriod getWithId(Long id) {
        String serviceUrl = crudUrl + "/" + id.toString();
        ResponseEntity<SchoolDayPeriod> response = restTemplate.exchange(backendBaseUrl + serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<SchoolDayPeriod>() {
                });
        return response.getBody();
    }

    @Override
    public List<SchoolDayPeriod> getWithIds(List<Long> ids) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.putIfAbsent("id", ids.stream().map(e -> e.toString()).collect(toList()));
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .queryParams(map)
                .build().encode().toUri();
        ResponseEntity<List<SchoolDayPeriod>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<SchoolDayPeriod>>() {
                });
        return response.getBody();
    }

    @Override
    public List<SchoolDayPeriod> getAll() {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .build().encode().toUri();
        ResponseEntity<List<SchoolDayPeriod>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<SchoolDayPeriod>>() {
                });
        return response.getBody();
    }

    @Override
    public void insertWithGenerator(PeriodsGeneratorSettings generationSettings) {
        String serviceUrl = backendBaseUrl + crudUrl + "/generator";
        HttpEntity<PeriodsGeneratorSettings> entity = new HttpEntity<>(generationSettings);
        restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, Void.class);
    }
}
