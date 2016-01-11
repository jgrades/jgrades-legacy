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

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.SimpleThreadScope;

@Configuration
public class ContainerEnvSimulatorConfig {
    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        customScopeConfigurer.addScope("session", simpleThreadScope());
        return customScopeConfigurer;
    }

    @Bean
    public SimpleThreadScope simpleThreadScope() {
        return new SimpleThreadScope();
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
//        stringConverter.setWriteAcceptCharset(false);
//        converters.add(stringConverter);
//        converters.add(new AllEncompassingFormHttpMessageConverter());
//        converters.add(jsonConverter());
//    }
//
//    @Bean
//    @Primary
//    public ObjectMapper jacksonObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
////        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        objectMapper.registerModule(new JavaTimeModule());
//        return objectMapper;
//    }

//    @Bean
//    public MappingJackson2HttpMessageConverter jsonConverter() {
//        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
//        jacksonConverter.setSupportedMediaTypes(Arrays.asList(MediaType.valueOf("application/json")));
//        jacksonConverter.setObjectMapper(jacksonObjectMapper());
//        return jacksonConverter;
//    }
//
//    @Bean
//    public DefaultServletHttpRequestHandler createDefaultServletHttpRequestHandler() {
//        return new DefaultServletHttpRequestHandler();
//    }
//
//    @Bean
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("utf-8");
//        resolver.setMaxUploadSize(20000);
//        return resolver;
//    }
//
//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//        resolvers.add(exceptionHandlerExceptionResolver());
//    }
//
//    @Bean
//    public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {
//        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
//        resolver.setMessageConverters(HttpMessageConverterUtils.getDefaultHttpMessageConverters());
//        return resolver;
//    }
}
