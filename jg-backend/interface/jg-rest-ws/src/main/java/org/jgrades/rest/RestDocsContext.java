/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
@EnableSwagger2
public class RestDocsContext {

    private static ApiInfo apiInfo() {
        return new ApiInfo(
                "jGrades REST Web Services",
                "Web services for backend operations in jGrades System",
                "0.4-RELEASE",
                StringUtils.EMPTY,
                "jgrades.project@gmail.com",
                "Apache Licence 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );
    }

    @Bean
    public Docket jGradesRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDateTime.class, Long[].class)
                .directModelSubstitute(LocalDate.class, Long[].class)
                .directModelSubstitute(LocalTime.class, Long[].class)
                .enableUrlTemplating(false)
                .apiInfo(apiInfo());
    }

}
