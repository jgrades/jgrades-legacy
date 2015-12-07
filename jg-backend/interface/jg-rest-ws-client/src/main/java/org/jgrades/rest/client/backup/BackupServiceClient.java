/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.backup;

import org.jgrades.backup.api.entities.Backup;
import org.jgrades.backup.api.entities.BackupEvent;
import org.jgrades.backup.api.model.RestoreSettings;
import org.jgrades.rest.api.backup.IBackupService;
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
public class BackupServiceClient extends RestCrudPagingServiceClient<Backup, Long>
        implements IBackupService {
    @Autowired
    public BackupServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                               StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, "/backup", restTemplate);
    }

    @Override
    public Backup getWithId(Long id) {
        String serviceUrl = crudUrl + "/" + id.toString();
        ResponseEntity<Backup> response = restTemplate.exchange(backendBaseUrl + serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Backup>() {
                });
        return response.getBody();
    }

    @Override
    public List<Backup> getWithIds(List<Long> ids) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.putIfAbsent("id", ids.stream().map(e -> e.toString()).collect(toList()));
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .queryParams(map)
                .build().encode().toUri();
        ResponseEntity<List<Backup>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Backup>>() {
                });
        return response.getBody();
    }

    @Override
    public Page<Backup> getPage(Integer number, Integer size) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .queryParam("page", number)
                .queryParam("limit", size)
                .build().encode().toUri();
        ResponseEntity<Page<Backup>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Page<Backup>>() {
                });
        return response.getBody();
    }

    @Override
    public void makeBackup() {
        String serviceUrl = backendBaseUrl + crudUrl;
        restTemplate.exchange(serviceUrl, HttpMethod.PUT, HttpEntity.EMPTY, Void.class);
    }

    @Override
    public void refresh() {
        String serviceUrl = backendBaseUrl + crudUrl + "/refresh";
        restTemplate.exchange(serviceUrl, HttpMethod.POST, HttpEntity.EMPTY, Void.class);
    }

    @Override
    public void restore(Long id, RestoreSettings restoreSettings) {
        String serviceUrl = backendBaseUrl + crudUrl + "/" + id + "/restore";
        HttpEntity<RestoreSettings> entity = new HttpEntity<>(restoreSettings);
        restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, Void.class);
    }

    @Override
    public void interrupt(Long id) {
        String serviceUrl = backendBaseUrl + crudUrl + "/" + id + "/interrupt";
        restTemplate.exchange(serviceUrl, HttpMethod.POST, HttpEntity.EMPTY, Void.class);
    }

    @Override
    public List<BackupEvent> getEvents(Long id) {
        String serviceUrl = backendBaseUrl + crudUrl + "/" + id + "/events";
        ResponseEntity<List<BackupEvent>> response = restTemplate.exchange(serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<BackupEvent>>() {
        });
        return response.getBody();
    }
}
