package org.jgrades.logging.logger.utils;

import ch.qos.logback.classic.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyUtilsTest {

    private Properties properties;

    @Before
    public void init(){
        properties = new Properties();
        properties.setProperty("logging.configuration.strategy","LOG_PER_TYPE_MODULE");
        properties.setProperty("logging.level","INFO");

        PropertyUtils.setPropertiesFile(properties);
    }

    @After
    public void clean() throws IOException {
        PropertyUtils.setNewLoggerConfiguration("LOG_PER_TYPE");
        PropertyUtils.setNewLoggingLevel(Level.INFO);
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
    public void trySetCorrectLevel_NoException() throws IOException {

        PropertyUtils.setNewLoggingLevel(Level.ERROR);
        assertThat(PropertyUtils.getCurrentLoggingLevel()).isEqualTo(Level.ERROR);
    }
    @Test
    public void trySetCorrectConfiguration_NoException() throws IOException {
        String logPerModule = "LOG_PER_MODULE";
        PropertyUtils.setNewLoggerConfiguration(logPerModule);

        assertThat(PropertyUtils.getCurrentLoggerConfiguration().toString()).isEqualTo(logPerModule);
    }

}
