package org.jgrades.logging.logger;

import org.apache.commons.lang3.StringUtils;
import org.jgrades.logging.logger.configuration.LoggingConfiguration;
import org.jgrades.logging.logger.utils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by Piotr on 2015-07-23.
 */
public class JGradesLogger {

    private static final String LOGGER_NAME = "logger";
    private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    public static JGradesLogger jGradesLogger;
    private static String moduleName;

    public JGradesLogger(Class clazz){
        this.moduleName = StringUtils.split(clazz.getName(), ".")[2];
    }

    public static void info(String msg) throws IOException {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.info(msg);
    }

    public static void debug(String msg) throws IOException {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.debug(msg);
    }

    public static void error(String msg) throws IOException {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.error(msg);
    }

    public static void trace(String msg) throws IOException {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.trace(msg);
    }

    private static void setMDC(Level loggingLevel, LoggingConfiguration currenctConfiguration) {
        switch(currenctConfiguration) {
            case LOG_PER_TYPE:
                MDC.put(StringUtils.EMPTY, StringUtils.EMPTY);
                break;
            case LOG_PER_MODULE:
                MDC.put("logging_configuration",moduleName);
                break;
            case LOG_PER_TYPE_MODULE:
                MDC.put("logging_configuration",moduleName+"_"+loggingLevel.toString().toLowerCase());
                break;
        }
    }


}
