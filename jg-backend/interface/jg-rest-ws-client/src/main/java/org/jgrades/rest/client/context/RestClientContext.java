/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.client.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jgrades.rest.client.JGRestErrorHandler;
import org.jgrades.rest.client.StatefullRestTemplate;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySources({
        @PropertySource("classpath:jg-rest-client.properties"),
        @PropertySource(value = "file:${jgrades.application.properties.file}", ignoreResourceNotFound = true)
})
@ComponentScan("org.jgrades.rest.client")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RestClientContext {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Scope("session")
    public StatefullRestTemplate statefullRestTemplate() {
        StatefullRestTemplate restTemplate = new StatefullRestTemplate();

        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset(false);
        converters.add(stringConverter);
        converters.add(new AllEncompassingFormHttpMessageConverter());
        converters.add(jsonConverter());
        restTemplate.setMessageConverters(converters);

        restTemplate.setErrorHandler(jgRestErrorHandler());
        return restTemplate;
    }

    @Bean
    public JGRestErrorHandler jgRestErrorHandler() {
        return new JGRestErrorHandler(jacksonObjectMapper());
    }

    @Bean
    public MappingJackson2HttpMessageConverter jsonConverter() {
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.valueOf("application/json")));
        jacksonConverter.setObjectMapper(jacksonObjectMapper());
        return jacksonConverter;
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }
}
