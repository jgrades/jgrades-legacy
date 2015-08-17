package org.jgrades.logging.model;

import ch.qos.logback.classic.Level;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class LoggingConfiguration {
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

    public LoggingStrategy getLoggingStrategy() {
        return loggingStrategy;
    }

    public void setLoggingStrategy(LoggingStrategy loggingStrategy) {
        this.loggingStrategy = loggingStrategy;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public Integer getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(Integer maxDays) {
        this.maxDays = maxDays;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        LoggingConfiguration rhs = (LoggingConfiguration) obj;
        return new EqualsBuilder()
                .append(this.loggingStrategy, rhs.loggingStrategy)
                .append(this.level, rhs.level)
                .append(this.maxFileSize, rhs.maxFileSize)
                .append(this.maxDays, rhs.maxDays)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(loggingStrategy)
                .append(level)
                .append(maxFileSize)
                .append(maxDays)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("loggingStrategy", loggingStrategy)
                .append("level", level)
                .append("maxFileSize", maxFileSize)
                .append("maxDays", maxDays)
                .toString();
    }
}
