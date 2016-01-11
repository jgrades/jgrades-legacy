/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.context;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.List;

@Configuration
@PropertySources({
        @PropertySource("classpath:jg-admin.properties"),
        @PropertySource(value = "file:${jgrades.application.properties.file}", ignoreResourceNotFound = true)
})
@ComponentScan("org.jgrades.admin")
public class AdminContext {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(name = "roleDetailsMapper")
    Mapper mapper() {
        List<String> mappingFiles = Lists.newArrayList("role_details_mapping.xml");
        return new DozerBeanMapper(mappingFiles);
    }
}
