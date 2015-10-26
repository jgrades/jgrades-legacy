/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.common;

import org.jgrades.rest.api.common.RestCrudPagingService;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public abstract class RestCrudPagingServiceClient<T, ID> extends RestCrudServiceClient<T, ID> //NOSONAR
        implements RestCrudPagingService<T, ID> { //NOSONAR
    @Autowired
    public RestCrudPagingServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl, String crudUrl,
                                       StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, crudUrl, restTemplate);
    }

    @Override
    public Page<T> getPage(Integer number, Integer size) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .queryParam("page", number)
                .queryParam("limit", size)
                .build().encode().toUri();
        ResponseEntity<Page<T>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Page<T>>() {
        });
        return response.getBody();
    }
}
