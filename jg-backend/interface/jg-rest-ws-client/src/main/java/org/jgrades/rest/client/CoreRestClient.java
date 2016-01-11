/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class CoreRestClient {
    protected String backendBaseUrl;

    protected StatefullRestTemplate restTemplate;

    @Autowired
    public CoreRestClient(@Value("${rest.backend.base.url}") String backendBaseUrl,
                          StatefullRestTemplate restTemplate) {
        this.backendBaseUrl = backendBaseUrl;
        this.restTemplate = restTemplate;
    }
}
