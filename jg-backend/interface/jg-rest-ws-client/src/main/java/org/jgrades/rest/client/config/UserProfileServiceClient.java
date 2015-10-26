/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.config;

import org.jgrades.config.api.model.UserData;
import org.jgrades.rest.api.config.IUserProfileService;
import org.jgrades.rest.client.CoreRestClient;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class UserProfileServiceClient extends CoreRestClient implements IUserProfileService {
    @Autowired
    public UserProfileServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                    StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, restTemplate);
    }

    @Override
    public void setProfileData(UserData userData) {
        String serviceUrl = backendBaseUrl + "/profile";
        HttpEntity<UserData> entity = new HttpEntity<>(userData);
        restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, Void.class);
    }
}
