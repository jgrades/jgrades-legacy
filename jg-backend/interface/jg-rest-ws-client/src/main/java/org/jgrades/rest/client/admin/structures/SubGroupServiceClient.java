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

import org.jgrades.data.api.entities.SubGroup;
import org.jgrades.rest.api.admin.structures.ISubGroupService;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.jgrades.rest.client.common.RestCrudPagingServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SubGroupServiceClient extends RestCrudPagingServiceClient<SubGroup, Long>
        implements ISubGroupService {
    @Autowired
    public SubGroupServiceClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                                 StatefullRestTemplate restTemplate) {
        super(backendBaseUrl, "/subgroup", restTemplate);
    }
}
