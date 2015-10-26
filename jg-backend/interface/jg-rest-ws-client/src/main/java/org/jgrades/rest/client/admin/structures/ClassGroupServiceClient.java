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

import org.jgrades.data.api.entities.ClassGroup;
import org.jgrades.data.api.entities.roles.StudentDetails;
import org.jgrades.rest.api.admin.structures.IClassGroupService;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.jgrades.rest.client.common.RestCrudPagingServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ClassGroupServiceClient extends RestCrudPagingServiceClient<ClassGroup, Long> implements IClassGroupService {
    @Autowired
    public ClassGroupServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                   StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, "/classgroup", restTemplate);
    }

    @Override
    public void setStudents(Long id, Set<StudentDetails> students) {
        String serviceUrl = backendBaseUrl + crudUrl + "/" + id + "/students";
        HttpEntity<Set<StudentDetails>> entity = new HttpEntity<>(students);
        restTemplate.exchange(serviceUrl, HttpMethod.POST, entity, Void.class);
    }

    @Override//TODO
    public Set<StudentDetails> getStudents(Long id) {
        return null;
    }

    @Override
    public List<ClassGroup> getFromActiveSemester() {
        return null;
    }
}
