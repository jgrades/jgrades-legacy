package org.jgrades.logging.logger.configuration.strategy;

import org.jgrades.logging.logger.configuration.LoggingConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by Piotr on 2015-07-26.
 */
public class ConfigurationStrategyClientTest {

    private ConfigurationStrategyClient client;
    private ModuleConfiguration mockedModuleConfiguration;
    @Before
    public void init(){
        client = spy(ConfigurationStrategyClient.class);
        mockedModuleConfiguration = spy(ModuleConfiguration.class);

        when(client.getConfigurationStrategy()).thenReturn(mockedModuleConfiguration);
        when(mockedModuleConfiguration.getConfigurationFilePath()).thenReturn("logback_per_module_logging_test.xml");
    }

    @Test
    public void setCorrectStrategyTest(){

        client.setStrategy(LoggingConfiguration.LOG_PER_MODULE);

        assertThat(client.getConfigurationStrategy()).isInstanceOf(ModuleConfiguration.class);
    }

    @Test
    public void getFileChannelTest_NotNull() throws IOException {

        client.setStrategy(LoggingConfiguration.LOG_PER_MODULE);

        assertThat(client.getFileChannel()).isNotNull();
    }
}
