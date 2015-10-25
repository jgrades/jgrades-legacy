/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jirutka.spring.exhandler.messages.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class JGRestErrorHandler extends DefaultResponseErrorHandler {
    private ObjectMapper objectMapper;

    @Autowired
    public JGRestErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        ErrorMessage errorMessage = objectMapper.readValue(response.getBody(), ErrorMessage.class);
        throw new JGBackendException(errorMessage);

    }
}
