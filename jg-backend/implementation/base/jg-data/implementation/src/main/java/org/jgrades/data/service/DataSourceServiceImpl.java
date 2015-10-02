/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.service;

import org.apache.commons.configuration.Configuration;
import org.jgrades.data.api.model.DataSourceDetails;
import org.jgrades.data.api.service.DataSourceService;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DataSourceServiceImpl implements DataSourceService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(DataSourceServiceImpl.class);

    private static final String JDBC_URL_PROPERTY_NAME = "data.db.jdbc.url";
    private static final String USERNAME_PROPERTY_NAME = "data.db.username";
    private static final String PASSWORD_PROPERTY_NAME = "data.db.password";

    @Autowired
    public Configuration appConfiguration;

    @Value("${" + JDBC_URL_PROPERTY_NAME + "}")
    private String defaultJdbcUrl;

    @Value("${" + USERNAME_PROPERTY_NAME + "}")
    private String defaultUsername;

    @Value("${" + PASSWORD_PROPERTY_NAME + "}")
    private String defaultPassword;

    @Value("${data.connection.timeout.seconds}")
    private String timeout;

    @Override
    public DataSourceDetails getDataSourceDetails() {
        DataSourceDetails details = new DataSourceDetails();

        details.setUrl(appConfiguration.getString(JDBC_URL_PROPERTY_NAME, defaultJdbcUrl));
        details.setUsername(appConfiguration.getString(USERNAME_PROPERTY_NAME, defaultUsername));
        details.setPassword(appConfiguration.getString(PASSWORD_PROPERTY_NAME, defaultPassword));

        return details;
    }

    @Override
    public void setDataSourceDetails(DataSourceDetails dataSourceDetails) {
        appConfiguration.setProperty(JDBC_URL_PROPERTY_NAME, dataSourceDetails.getUrl());
        appConfiguration.setProperty(USERNAME_PROPERTY_NAME, dataSourceDetails.getUsername());
        appConfiguration.setProperty(PASSWORD_PROPERTY_NAME, dataSourceDetails.getPassword());
    }

    @Override
    public boolean testConnection() {
        try {
            DataSourceDetails dsDetails = getDataSourceDetails();
            Connection connection = DriverManager.getConnection(dsDetails.connectionUrl(), dsDetails.getUsername(), dsDetails.getPassword());
            return connection.isValid(Integer.parseInt(timeout));
        } catch (SQLException e) {
            LOGGER.error("Problem while testing connection to database", e);
            return false;
        }
    }
}
