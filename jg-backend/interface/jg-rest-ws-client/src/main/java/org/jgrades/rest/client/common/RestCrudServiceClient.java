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

import org.jgrades.rest.api.common.RestCrudService;
import org.jgrades.rest.client.CoreRestClient;
import org.jgrades.rest.client.StatefullRestTemplate;
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
public abstract class RestCrudServiceClient<T, ID> extends CoreRestClient implements RestCrudService<T, ID> {

    @Autowired
    public RestCrudServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                 StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, restTemplate);
    }

    public abstract String serviceUrl();


    @Override
    public void insertOrUpdate(T entity) {
        HttpEntity<T> httpEntity = new HttpEntity<>(entity);
        restTemplate.exchange(backendBaseUrl + serviceUrl(), HttpMethod.POST, httpEntity, Void.class);
    }

    @Override
    public void remove(List<ID> ids) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.putIfAbsent("id", ids.stream().map(e -> e.toString()).collect(toList()));
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + serviceUrl())
                .queryParams(map)
                .build().encode().toUri();
        restTemplate.exchange(uri, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
    }

    @Override
    public T getWithId(ID id) {
        String serviceUrl = serviceUrl() + "/" + id.toString();
        ResponseEntity<T> response = restTemplate.exchange(backendBaseUrl + serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<T>() {
        });
        return response.getBody();
    }

    @Override
    public List<T> getWithIds(List<ID> ids) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.putIfAbsent("id", ids.stream().map(e -> e.toString()).collect(toList()));
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + serviceUrl())
                .queryParams(map)
                .build().encode().toUri();
        ResponseEntity<List<T>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<T>>() {
        });
        return response.getBody();
    }
}
