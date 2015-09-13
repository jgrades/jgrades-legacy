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
