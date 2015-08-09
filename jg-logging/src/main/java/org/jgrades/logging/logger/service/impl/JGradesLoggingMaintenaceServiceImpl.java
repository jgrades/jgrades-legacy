package org.jgrades.logging.logger.service.impl;

import com.google.common.io.Resources;
import org.jgrades.logging.logger.JGLoggingFactory;
import org.jgrades.logging.logger.JGradesLogger;
import org.jgrades.logging.logger.configuration.LoggingConfiguration;
import org.jgrades.logging.logger.parser.ConfigurationDOMParser;
import org.jgrades.logging.logger.parser.ConfigurationParser;
import org.jgrades.logging.logger.service.api.JgLoggingService;
import org.jgrades.logging.logger.utils.PropertyUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

import ch.qos.logback.classic.Level;


public class JGradesLoggingMaintenaceServiceImpl implements JgLoggingService {

    private static final String LOG_BACK_CONFIGURATION_FILE_NAME = "logback.xml";
    private final JGradesLogger logger = JGLoggingFactory.getLogger(JGradesLoggingMaintenaceServiceImpl.class);
    private ConfigurationParser parser;

    public JGradesLoggingMaintenaceServiceImpl(){
        this(new ConfigurationDOMParser());
    }

    public JGradesLoggingMaintenaceServiceImpl(ConfigurationParser parser) {
        this.parser = parser;
    }

    /**
     * These method is using to change logging level
     * @param level new logging level, for example : INFO, ERROR
     *
     */
    @Override
    public void setLevel(Level level) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);

        PropertyUtils.setNewLoggingLevel(level);
    }

    /**
     * These method is using to change current logging configuration.
     * Old logs won't be change
     * @param mode new logging cofiguration, for example : LOG_PER_TYPE, LOG_PER_MODULE
     */
    @Override
    public void setLoggingMode(LoggingConfiguration mode) {
        PropertyUtils.setNewLoggerConfiguration(mode.toString());

    }

    /**
     * These method is using to change maksimum of log size
     * @param size new log size, input is a String, for example: "100 MB", "100 KB"
     */
    @Override
    public void setMaxSize(String size) {
        try {
            parser.parse(getLogbackConfigurationFile());
            parser.setLogFileSize(size, getLogbackConfigurationFile());
        }
        catch(IOException | ParserConfigurationException | SAXException |  IllegalArgumentException ex) {
            logger.error("Error when try set new max file size "+ex);
        }
    }

    /**
     * These method is using to change number of days when logback should clean logs file
     * @param days number of days to clean procedure
     */
    @Override
    public void setCleaningAfterDays(Integer days) {
        try {
            parser.parse(getLogbackConfigurationFile());
            parser.setLogFileStorageTimeLimit(days, getLogbackConfigurationFile());
        }
        catch(IOException | ParserConfigurationException | SAXException |  IllegalArgumentException ex) {
            logger.error("Error when try set new clean time "+ex);
        }
    }

    /**
     * These method is using to get current logging confiuguration saved in .property file
     * @return current logging configuration
     */
    @Override
    public LoggingConfiguration getLoggingConfiguration() {
        return PropertyUtils.getCurrentLoggerConfiguration();
    }

    private String getLogbackConfigurationFile(){
        URL url = Resources.getResource(LOG_BACK_CONFIGURATION_FILE_NAME);
        return url.getPath();
    }

    public ConfigurationParser getParser() {
        return parser;
    }

}
