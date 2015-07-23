package org.jgrades.logging.logger.service.impl;

import com.google.common.io.Resources;
import org.jgrades.logging.logger.configuration.LoggingConfiguration;
import org.jgrades.logging.logger.configuration.strategy.ConfigurationStrategyClient;
import org.jgrades.logging.logger.parser.ConfigurationDOMParser;
import org.jgrades.logging.logger.parser.ConfigurationParser;
import org.jgrades.logging.logger.service.api.JgLoggingService;
import org.jgrades.logging.logger.utils.PropertyUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import ch.qos.logback.classic.Level;

/**
 * Created by Piotr on 2015-07-16.
 */
public class JGradesLoggingMaintenaceServiceImpl implements JgLoggingService {

    private static final String LOG_BACK_CONFIGURATION_FILE_NAME = "logback.xml";

    private ConfigurationParser parser;
    private ConfigurationStrategyClient configurationStrategyClient;

    public JGradesLoggingMaintenaceServiceImpl(){
        this(new ConfigurationStrategyClient(),new ConfigurationDOMParser());
    }

    public JGradesLoggingMaintenaceServiceImpl(ConfigurationStrategyClient client, ConfigurationParser parser) {
        this.configurationStrategyClient = client;
        this.parser = parser;
    }
    @Override
    public void setLevel(Level level) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);
    }

    @Override
    public void setLoggingMode(LoggingConfiguration mode) throws IOException {
        PropertyUtils.setNewLoggerConfiguration(mode.toString());
        configurationStrategyClient.setStrategy(mode);
        parser.copyContent(configurationStrategyClient.getFileChannel());
    }

    @Override
    public void setMaxSize(String size) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException {

        parser.parse(getLogbackConfigurationFile());
        parser.setLogFileSize(size,getLogbackConfigurationFile(),configurationStrategyClient.getConfigurationStrategy().getConfigurationFilePath());
    }

    @Override
    public void setCleaningAfterDays(Integer days) throws IOException, ParserConfigurationException, SAXException, IllegalAccessException {

        parser.parse(getLogbackConfigurationFile());
        parser.setLogFileStorageTimeLimit(days, getLogbackConfigurationFile(),configurationStrategyClient.getConfigurationStrategy().getConfigurationFilePath());
    }

    @Override
    public LoggingConfiguration getLoggingConfiguration() throws IOException {
        return PropertyUtils.getCurrentLoggerConfiguration();
    }

    private String getLogbackConfigurationFile() throws IOException {
        URL url = Resources.getResource(LOG_BACK_CONFIGURATION_FILE_NAME);
        return url.getPath();
    }


    public ConfigurationStrategyClient getConfigurationStrategyClient() {
        return configurationStrategyClient;
    }

    public ConfigurationParser getParser() {
        return parser;
    }
}
