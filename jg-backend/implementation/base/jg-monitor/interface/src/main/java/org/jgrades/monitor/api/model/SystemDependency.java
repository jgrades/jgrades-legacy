/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.monitor.api.model;

public enum SystemDependency {
    MAIN_DATA_SOURCE("mainDataSourceChecker", "Connection to database failed"),
    LIC_KEYSTORE("licKeystoreChecker", "Licensing data missing"),
    LIC_SECDATA("licSecDataChecker", "Licensing data missing"),
    LOGBACK_XML("logbackXmlChecker", "Logging data missing"),
    NONE("dummyChecker", "dummy");

    private final String checkerBeanName;
    private final String details;

    SystemDependency(String checkerBeanName, String details) {
        this.checkerBeanName = checkerBeanName;
        this.details = details;
    }

    public String getCheckerBeanName() {
        return checkerBeanName;
    }

    public String getDetails() {
        return details;
    }
}
