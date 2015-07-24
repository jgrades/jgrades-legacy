package org.jgrades.logging.logger.configuration.strategy;

import org.jgrades.logging.logger.configuration.LoggingConfiguration;

import java.io.IOException;
import java.nio.channels.FileChannel;


public class ConfigurationStrategyClient {

    private ConfigurationStrategy strategy;

    public ConfigurationStrategyClient(){

    }

    public void setStrategy(LoggingConfiguration configuration){
        switch (configuration) {
            case LOG_PER_MODULE:
                strategy = new ModuleConfiguration();
            case LOG_PER_TYPE:
                strategy = new TypeConfiguration();
            case LOG_PER_TYPE_MODULE:
                strategy = new TypeModuleConfiguration();
        }
    }

    public FileChannel getFileChannel() throws IOException {
        return getConfigurationStrategy().getFileChannel();
    }

    public ConfigurationStrategy getConfigurationStrategy(){
        return strategy;
    }
}
