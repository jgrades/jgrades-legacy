/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.admin.accounts;

import org.jgrades.admin.api.model.MassAccountCreatorResultRecord;
import org.jgrades.data.api.entities.User;
import org.jgrades.rest.api.admin.accounts.IUserService;
import org.jgrades.rest.api.admin.accounts.MassCreatorDTO;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.jgrades.rest.client.common.RestCrudPagingServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
public class UserServiceClient extends RestCrudPagingServiceClient<User, Long> implements IUserService {
    @Autowired
    public UserServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                             StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, "/user", restTemplate);
    }

    @Override
    public User getWithId(Long id) {
        String serviceUrl = crudUrl + "/" + id.toString();
        ResponseEntity<User> response = restTemplate.exchange(backendBaseUrl + serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<User>() {
                });
        return response.getBody();
    }

    @Override
    public List<User> getWithIds(List<Long> ids) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.putIfAbsent("id", ids.stream().map(e -> e.toString()).collect(toList()));
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .queryParams(map)
                .build().encode().toUri();
        ResponseEntity<List<User>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<User>>() {
                });
        return response.getBody();
    }

    @Override
    public List<User> getAll() {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .build().encode().toUri();
        ResponseEntity<List<User>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<User>>() {
                });
        return response.getBody();
    }

    @Override
    public Page<User> getPage(Integer number, Integer size) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .queryParam("page", number)
                .queryParam("limit", size)
                .build().encode().toUri();
        ResponseEntity<PageImpl<User>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PageImpl<User>>() {
                });
        return response.getBody();
    }

    @Override
    public List<User> getSearchResults(String phrase, String login, String name, String surname, String email, String roles, Boolean active, LocalDateTime lastVisitFrom, LocalDateTime lastVisitTo) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .queryParam("phrase", phrase).queryParam("login", login).queryParam("name", name)
                .queryParam("surname", surname).queryParam("email", email).queryParam("roles", roles)
                .queryParam("active", active)
                .queryParam("lastVisitFrom", lastVisitFrom)
                .queryParam("lastVisitTo", lastVisitTo)
                .build().encode().toUri();
        ResponseEntity<List<User>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<User>>() {
        });
        return response.getBody();
    }

    @Override
    public Page<User> getSearchResultsPage(Integer number, Integer size, String phrase, String login, String name, String surname, String email, String roles, Boolean active, LocalDateTime lastVisitFrom, LocalDateTime lastVisitTo) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + crudUrl)
                .queryParam("page", number).queryParam("limit", size).queryParam("phrase", phrase)
                .queryParam("login", login).queryParam("name", name).queryParam("surname", surname)
                .queryParam("email", email).queryParam("roles", roles).queryParam("active", active)
                .queryParam("lastVisitFrom", lastVisitFrom).queryParam("lastVisitTo", lastVisitTo)
                .build().encode().toUri();
        ResponseEntity<PageImpl<User>> response = restTemplate.exchange(uri,
                HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PageImpl<User>>() {
        });
        return response.getBody();
    }

    @Override
    public Set<MassAccountCreatorResultRecord> massStudentsCreator(MassCreatorDTO massCreatorDTO) {
        String serviceUrl = backendBaseUrl + crudUrl + "/mass";
        HttpEntity<MassCreatorDTO> httpEntity = new HttpEntity<>(massCreatorDTO);
        ResponseEntity<Set<MassAccountCreatorResultRecord>> response = restTemplate
                .exchange(serviceUrl, HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<Set<MassAccountCreatorResultRecord>>() {
                        });
        return response.getBody();
    }
}
