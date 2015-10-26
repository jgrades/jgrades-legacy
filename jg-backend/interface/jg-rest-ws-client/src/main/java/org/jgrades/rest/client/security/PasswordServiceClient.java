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

import com.google.common.collect.Sets;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.rest.api.security.IPasswordService;
import org.jgrades.rest.api.security.PasswordDTO;
import org.jgrades.rest.client.CoreRestClient;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.jgrades.security.api.entities.PasswordPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PasswordServiceClient extends CoreRestClient implements IPasswordService {
    @Autowired
    public PasswordServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                 StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, restTemplate);
    }

    @Override
    public void setPassword(PasswordDTO passwordInfo) {
        String serviceUrl = backendBaseUrl + "/password";
        HttpEntity<PasswordDTO> entity = new HttpEntity<>(passwordInfo);
        restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, Void.class);
    }

    @Override
    public PasswordPolicy getForRole(JgRole role) {
        String serviceUrl = backendBaseUrl + "/password/policy/" + role.name();
        ResponseEntity<PasswordPolicy> response = restTemplate.exchange(serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, PasswordPolicy.class);
        return response.getBody();
    }

    @Override
    public Set<PasswordPolicy> getAll() {
        String serviceUrl = backendBaseUrl + "/password/policy";
        ResponseEntity<PasswordPolicy[]> response = restTemplate.exchange(serviceUrl,
                HttpMethod.GET, HttpEntity.EMPTY, PasswordPolicy[].class);
        return Sets.newHashSet(response.getBody());
    }

    @Override
    public void setForRole(PasswordPolicy policy) {
        String serviceUrl = backendBaseUrl + "/password/policy";
        HttpEntity<PasswordPolicy> entity = new HttpEntity<>(policy);
        restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, Void.class);
    }
}
