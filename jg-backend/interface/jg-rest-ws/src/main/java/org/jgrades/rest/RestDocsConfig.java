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

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class RestDocsConfig {
    public static final String securitySchemaOAuth2 = "oauth2schema";
    public static final String authorizationScopeGlobal = "global";
    public static final String authorizationScopeGlobalDesc = "accessEverything";

    @Bean
    public Docket jGradesRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(DateTime.class, Long.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .enableUrlTemplating(false)
                .securitySchemes(Lists.newArrayList(securitySchema()))
                .securityContexts(Lists.newArrayList((securityContext())))
                .apiInfo(apiInfo());
    }

    private BasicAuth securitySchema() {
        AuthorizationScope authorizationScope = new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobal);
        LoginEndpoint loginEndpoint = new LoginEndpoint("http://localhost:9999/sso/login");
        GrantType grantType = new ImplicitGrant(loginEndpoint, "access_token");
        return new BasicAuth("jg-rest-sec");

        //return new OAuth(securitySchemaOAuth2, Lists.newArrayList(authorizationScope), Lists.newArrayList(grantType));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobalDesc);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference(securitySchemaOAuth2, authorizationScopes));
    }

    private List<SecurityScheme> securityScheme() {
        return Lists.newArrayList(new BasicAuth("jg-rest-auth"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "jGrades REST Web Services",
                "Web services for backend operations in jGrades System",
                "0.4-RELEASE",
                "",
                "jgrades.project@gmail.com",
                "Apache Licence 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );
    }

}
