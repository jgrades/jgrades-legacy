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

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;


public class StatefullRestTemplate extends RestTemplate {
    private String cookie;

    @Override
    protected <T> RequestCallback httpEntityCallback(Object requestBody) {
        return super.httpEntityCallback(insertCookieToHeaderIfApplicable(requestBody));
    }

    @Override
    protected <T> RequestCallback httpEntityCallback(Object requestBody, Type responseType) {
        return super.httpEntityCallback(insertCookieToHeaderIfApplicable(requestBody), responseType);
    }

    private Object insertCookieToHeaderIfApplicable(Object requestBody) {
        HttpEntity<?> httpEntity;

        if (requestBody instanceof HttpEntity) {
            httpEntity = (HttpEntity<?>) requestBody;
        } else if (requestBody != null) {
            httpEntity = new HttpEntity<Object>(requestBody);
        } else {
            httpEntity = HttpEntity.EMPTY;
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.putAll(httpEntity.getHeaders());

        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        if (httpHeaders.getContentType() == null) {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }

        if (StringUtils.isNotEmpty(cookie)) {
            httpHeaders.add("Cookie", cookie);
        }

        return new HttpEntity<>(httpEntity.getBody(), httpHeaders);
    }

    @Override
    protected void handleResponse(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        HttpHeaders headers = response.getHeaders();
        if (headers.containsKey("Set-Cookie")) {
            cookie = headers.get("Set-Cookie").get(0);
        }
        super.handleResponse(url, method, response);
    }
}
