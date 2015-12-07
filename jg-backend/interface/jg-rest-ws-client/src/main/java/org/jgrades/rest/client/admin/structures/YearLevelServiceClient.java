/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.admin.structures;

import org.jgrades.data.api.entities.YearLevel;
import org.jgrades.rest.api.admin.structures.IYearLevelService;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.jgrades.rest.client.common.RestCrudPagingServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
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
public class YearLevelServiceClient extends RestCrudPagingServiceClient<YearLevel, Long>
        implements IYearLevelService {
    @Autowired
    public YearLevelServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                  StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, "/yearlevel", restTemplate);
    }

    @Override
    public YearLevel getWithId(Long id) {
        String serviceUrl = crudUrl + "/" + id.toString();
        ResponseEntity<YearLevel> response = restTemplate.exchange(backendBaseUrl + serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<YearLevel>() {
                });
        return response.getBody();
    }

    @Override
    public List<YearLevel> getWithIds(List<Long> ids) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.putIfAbsent("id", ids.stream().map(e -> e.toString()).collect(toList()));
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .queryParams(map)
                .build().encode().toUri();
        ResponseEntity<List<YearLevel>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<YearLevel>>() {
                });
        return response.getBody();
    }

    @Override
    public Page<YearLevel> getPage(Integer number, Integer size) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .queryParam("page", number)
                .queryParam("limit", size)
                .build().encode().toUri();
        ResponseEntity<Page<YearLevel>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Page<YearLevel>>() {
                });
        return response.getBody();
    }
}
