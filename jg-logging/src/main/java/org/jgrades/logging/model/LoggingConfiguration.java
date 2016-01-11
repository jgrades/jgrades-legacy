/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.model;

import lombok.Data;

@Data
public final class LoggingConfiguration {
    private LoggingStrategy loggingStrategy;
    private JgLogLevel level;
    private String maxFileSize;
    private Integer maxDays;

    public LoggingConfiguration() {
    }

    public LoggingConfiguration(LoggingStrategy loggingStrategy, JgLogLevel level,
                                String maxFileSize, Integer maxDays) {
        this.loggingStrategy = loggingStrategy;
        this.level = level;
        this.maxFileSize = maxFileSize;
        this.maxDays = maxDays;
    }
}
