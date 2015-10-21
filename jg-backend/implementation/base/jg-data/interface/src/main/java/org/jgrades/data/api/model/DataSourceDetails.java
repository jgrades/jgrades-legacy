/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class DataSourceDetails {
    private static final Pattern URL_PATTERN = Pattern.compile("(.+):(\\d+)\\/(.+)");

    private String url;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String connectionUrl() {
        return "jdbc:postgresql://" + getUrl();
    }

    public String host() {
        Matcher matcher = URL_PATTERN.matcher(url);
        matcher.find();
        return matcher.group(1);
    }

    public String port() {
        Matcher matcher = URL_PATTERN.matcher(url);
        matcher.find();
        return matcher.group(2);
    }

    public String databaseName() {
        Matcher matcher = URL_PATTERN.matcher(url);
        matcher.find();
        return matcher.group(3);
    }
}
