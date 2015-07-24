package org.jgrades.logging.logger.utils;

import org.hamcrest.Matcher;
import org.jgrades.logging.logger.configuration.LoggingConfiguration;
import org.jgrades.logging.logger.configuration.strategy.ConfigurationStrategy;
import org.jgrades.logging.logger.configuration.strategy.TypeConfiguration;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class PropertyUtilsTest {

    @After
    public void clean() throws IOException {
        PropertyUtils.setNewLoggerConfiguration("LOG_PER_TYPE");
    }
    @Test
    public void currentConfigurationShouldntBeNull() throws IOException {

        assertThat(PropertyUtils.getCurrentLoggerConfiguration()).isNotNull();
    }

    @Test(expected = IllegalStateException.class)
    public void trySetWrongConfiguration_ThrowException() throws IOException {
        String wrongConfiguration = "INCORRECT_CONFIGURATION";
        PropertyUtils.setNewLoggerConfiguration(wrongConfiguration);
    }

    @Test
    public void trySetCorrectConfiguration_NoException() throws IOException {
        String logPerModule = "LOG_PER_MODULE";
        PropertyUtils.setNewLoggerConfiguration(logPerModule);

        assertThat(PropertyUtils.getCurrentLoggerConfiguration().toString()).isEqualTo(logPerModule);
    }

    @Test
    public void correctLogConfigurationShouldReturnCorrectconfigurationStrategy_NoException() throws IllegalAccessException {

        assertThat(PropertyUtils.readConfigurationStrategy(LoggingConfiguration.LOG_PER_TYPE) instanceof ConfigurationStrategy);
        assertThat(PropertyUtils.readConfigurationStrategy(LoggingConfiguration.LOG_PER_MODULE) instanceof ConfigurationStrategy);
        assertThat(PropertyUtils.readConfigurationStrategy(LoggingConfiguration.LOG_PER_TYPE_MODULE) instanceof ConfigurationStrategy);
    }

}
