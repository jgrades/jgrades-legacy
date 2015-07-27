package org.jgrades.logging.logger.service.impl;

import com.google.common.io.Resources;
import org.jgrades.logging.logger.JGLoggingFactory;
import org.jgrades.logging.logger.JGradesLogger;
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
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import ch.qos.logback.classic.Level;


public class JGradesLoggingMaintenaceServiceImpl implements JgLoggingService {

    private static final String LOG_BACK_CONFIGURATION_FILE_NAME = "logback.xml";
    private final JGradesLogger logger = JGLoggingFactory.getLogger(JGradesLoggingMaintenaceServiceImpl.class);
    private ConfigurationParser parser;
    private ConfigurationStrategyClient configurationStrategyClient;

    public JGradesLoggingMaintenaceServiceImpl(){
        this(new ConfigurationStrategyClient(), new ConfigurationDOMParser());
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
    public void setLoggingMode(LoggingConfiguration mode) {
        try {
            PropertyUtils.setNewLoggerConfiguration(mode.toString());
            configurationStrategyClient.setStrategy(mode);
            parser.copyContent(createOuputChannel(), createInputChannel());
        }
        catch(IOException ex) {
            logger.error("[JGradesLoggingMaintenaceServiceImpl][setLoggingMode()] "+ex);
        }
    }

    @Override
    public void setMaxSize(String size) {
        try {
            parser.parse(getLogbackConfigurationFile());
            parser.setLogFileSize(size, getLogbackConfigurationFile(), configurationStrategyClient.getConfigurationStrategy().getConfigurationFilePath());
        }
        catch(IOException | ParserConfigurationException | SAXException |  IllegalArgumentException ex) {
            logger.error("[JGradesLoggingMaintenaceServiceImpl[setMaxSize()] "+ex);
        }
    }

    @Override
    public void setCleaningAfterDays(Integer days) {
        try {
            parser.parse(getLogbackConfigurationFile());
            parser.setLogFileStorageTimeLimit(days, getLogbackConfigurationFile(), configurationStrategyClient.getConfigurationStrategy().getConfigurationFilePath());
        }
        catch(IOException | ParserConfigurationException | SAXException |  IllegalArgumentException ex) {
            logger.error("[JGradesLoggingMaintenaceServiceImpl[setCleaningAfterDays()] "+ex);
        }
    }

    @Override
    public LoggingConfiguration getLoggingConfiguration() {
        return PropertyUtils.getCurrentLoggerConfiguration();
    }

    private String getLogbackConfigurationFile(){
        URL url = Resources.getResource(LOG_BACK_CONFIGURATION_FILE_NAME);
        return url.getPath();
    }

    public ConfigurationStrategyClient getConfigurationStrategyClient() {
        return configurationStrategyClient;
    }


    public ConfigurationParser getParser() {
        return parser;
    }

    private FileChannel createOuputChannel() throws IOException {
        Path path = Paths.get(getLogbackConfigurationFile());
        return FileChannel.open(path);
    }

    private FileChannel createInputChannel() throws IOException {
        return configurationStrategyClient.getFileChannel();
    }
}
