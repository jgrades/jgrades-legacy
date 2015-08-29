package org.jgrades.logging.model;

import ch.qos.logback.classic.Level;
import lombok.Data;

@Data
public final class LoggingConfiguration {
    private LoggingStrategy loggingStrategy;
    private Level level;
    private String maxFileSize;
    private Integer maxDays;

    public LoggingConfiguration() {
    }

    public LoggingConfiguration(LoggingStrategy loggingStrategy, Level level,
                                String maxFileSize, Integer maxDays) {
        this.loggingStrategy = loggingStrategy;
        this.level = level;
        this.maxFileSize = maxFileSize;
        this.maxDays = maxDays;
    }
}
