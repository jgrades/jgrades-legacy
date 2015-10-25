/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.security;

import org.jgrades.rest.api.security.ILoginService;
import org.jgrades.rest.client.CoreRestClient;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.jgrades.security.api.model.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class LoginServiceClient extends CoreRestClient implements ILoginService {
    @Autowired
    public LoginServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                              StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, restTemplate);
    }

    @Override
    public LoginResult logIn(String login, String password) {
        String loginUrl = "/login";

        URI uri = UriComponentsBuilder.fromHttpUrl(backendBaseUrl + loginUrl)
                .queryParam("username", login)
                .queryParam("pswd", password)
                .build().encode().toUri();

        ResponseEntity<LoginResult> response = restTemplate.exchange(uri, HttpMethod.POST,
                HttpEntity.EMPTY, LoginResult.class);
        return response.getBody();
    }

    @Override
    public void logOut() {
        String logoutUrl = "/logout";
        restTemplate.exchange(backendBaseUrl + logoutUrl,
                HttpMethod.POST, HttpEntity.EMPTY, Void.class);
    }
}
