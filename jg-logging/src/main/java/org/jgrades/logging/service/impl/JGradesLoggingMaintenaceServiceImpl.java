package org.jgrades.logging.service.impl;

import ch.qos.logback.classic.LoggerContext;
import com.google.common.io.Resources;
import org.apache.commons.lang3.Validate;
import org.jgrades.logging.JGLoggingFactory;
import org.jgrades.logging.configuration.LoggingConfiguration;
import org.jgrades.logging.parser.ConfigurationParser;
import org.jgrades.logging.parser.ConfigurationDOMParser;
import org.jgrades.logging.service.api.JGradesLoggingMaintenanceService;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Piotr on 2015-07-16.
 */
public class JGradesLoggingMaintenaceServiceImpl implements JGradesLoggingMaintenanceService {

    ch.qos.logback.classic.Logger templateLogger = (ch.qos.logback.classic.Logger) JGLoggingFactory.getLogger(JGLoggingFactory.class);
    private LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
    private static final int MAX_LOG_SIZE_IN_MB = 100;
    private static final int MAX_LOG_STORE_TIME_IN_DAYS = 30;
    private static final int MIN_LOG_STORE_TIME_IN_DAYS = 1;

    private static final String LOG_BACK_CONFIGURATION_FILE_NAME = "logback.xml";

    private ConfigurationParser parser;

    public JGradesLoggingMaintenaceServiceImpl(){
        parser = new ConfigurationDOMParser();
    }

    @Override
    public void changeLogFileSize(int size) throws IOException, ParserConfigurationException, SAXException {

        Validate.exclusiveBetween(0, MAX_LOG_SIZE_IN_MB, size, "Log size value isn't between 0 and " + String.valueOf(MAX_LOG_SIZE_IN_MB) + " MB");

        //TODO
        //parser.parse(getLogbackConfigurationFile());




    }

    @Override
    public void changeLogStoreTimeLimit(int limit) {
        //TODO
    }

    @Override
    public void changeLoggingConfiguration(LoggingConfiguration configuration) {
        //TODO
    }

    private String getLogbackConfigurationFile() throws IOException {
        URL url = Resources.getResource(LOG_BACK_CONFIGURATION_FILE_NAME);
        return url.getPath();
    }

    public ConfigurationParser getConfigurationParser(){
        return parser;
    }

}
