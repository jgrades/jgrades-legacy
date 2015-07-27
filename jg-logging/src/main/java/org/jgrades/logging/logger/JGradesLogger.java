package org.jgrades.logging.logger;

import org.apache.commons.lang3.StringUtils;
import org.jgrades.logging.logger.configuration.LoggingConfiguration;
import org.jgrades.logging.logger.utils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;

import java.io.IOException;
import java.util.logging.Level;


public class JGradesLogger {

    private final String LOGGER_NAME = "logger";
    private final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

    public static JGradesLogger jGradesLogger;
    private static String moduleName;

    public JGradesLogger(Class clazz){
        this.moduleName = StringUtils.split(clazz.getName(), ".")[2];
    }

    public void info(String format) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.info(format);
    }

    public void info(String format, Object arg)  {
        setMDC(Level.INFO, PropertyUtils.getCurrentLoggerConfiguration());
        logger.info(format, arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.info(format,arg1,arg2);
    }

    public void info(String format, Object[] argArray) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.info(format,argArray);
    }

    public void info(String msg, Throwable t) {
        setMDC(Level.INFO, PropertyUtils.getCurrentLoggerConfiguration());
        logger.info(msg, t);
    }

    public void info(Marker marker, String msg) {
        setMDC(Level.INFO, PropertyUtils.getCurrentLoggerConfiguration());
        logger.info(marker,msg);
    }

    public void info(Marker marker, String format, Object arg) {
        setMDC(Level.INFO, PropertyUtils.getCurrentLoggerConfiguration());
        logger.info(marker,format,arg);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        setMDC(Level.INFO, PropertyUtils.getCurrentLoggerConfiguration());
        logger.info(marker,format,arg1,arg2);
    }

    public void info(Marker marker, String format, Object[] argArray) {
        setMDC(Level.INFO, PropertyUtils.getCurrentLoggerConfiguration());
        logger.info(marker,format, argArray);
    }

    public void info(Marker marker, String msg, Throwable t) {
        setMDC(Level.INFO, PropertyUtils.getCurrentLoggerConfiguration());
        logger.info(marker, msg, t);

    }

    //------------------------------------ ERROR ----------------------------------//
    public void error(String msg) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.error(msg);
    }

    public void error(String format, Object arg) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.error(format, arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        setMDC(Level.INFO, PropertyUtils.getCurrentLoggerConfiguration());
        logger.error(format, arg1, arg2);
    }

    public void error(String format, Object[] argArray) {
        setMDC(Level.INFO, PropertyUtils.getCurrentLoggerConfiguration());
        logger.error(format, argArray);
    }

    public void error(String msg, Throwable t){
        setMDC(Level.INFO, PropertyUtils.getCurrentLoggerConfiguration());
        logger.error(msg, t);
    }

    public void error(Marker marker, String msg) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.error(marker, msg);
    }

    public void error(Marker marker, String format, Object arg) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.error(marker, format, arg);
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.error(marker, format, arg1, arg2);
    }

    public void error(Marker marker, String format, Object[] argArray){
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.error(marker, format, argArray);
    }

    public void error(Marker marker, String msg, Throwable t) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.error(marker, msg, t);
    }

    //------------------------------------ TRACE ----------------------------------//
    public void trace(String msg) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.trace(msg);
    }

    public void trace(String format, Object arg) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.trace(format, arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.trace(format, arg1, arg2);
    }

    public void trace(String format, Object[] argArray) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.trace(format, argArray);
    }

    public void trace(String msg, Throwable t) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.trace(msg, t);
    }

    public void trace(Marker marker, String msg){
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.trace(marker, msg);
    }

    public void trace(Marker marker, String format, Object arg) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.trace(marker, format, arg);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2){
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.trace(marker, format, arg1, arg2);
    }

    public void trace(Marker marker, String format, Object[] argArray) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.trace(marker, format, argArray);
    }

    public void trace(Marker marker, String msg, Throwable t) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.trace(marker, msg, t);
    }

    //------------------------------------ DEBUG ----------------------------------//

    public void debug(String msg) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.debug(msg);
    }
    public void debug(String format, Object arg) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.debug(format,arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.debug(format,arg1,arg2);
    }

    public void debug(String format, Object[] argArray) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.debug(format,argArray);
    }

    public void debug(String msg, Throwable t) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.debug(msg,t);
    }

    public void debug(Marker marker, String msg)  {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.debug(marker,msg);
    }

    public void debug(Marker marker, String format, Object arg) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.debug(marker,format,arg);
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.debug(marker,format,arg1,arg2);
    }

    public void debug(Marker marker, String format, Object[] argArray){
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.debug(marker,format,argArray);
    }

    public void debug(Marker marker, String msg, Throwable t) {
        setMDC(Level.INFO,PropertyUtils.getCurrentLoggerConfiguration());
        logger.debug(marker,msg,t);
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
