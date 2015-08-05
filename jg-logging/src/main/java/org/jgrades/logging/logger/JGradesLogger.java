package org.jgrades.logging.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.logging.logger.utils.PropertyUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;

public class JGradesLogger {
    private static final String MDC_KEY = "logging_configuration";
    private static final String SEPARATOR = "_";
    private static final String MODULE = "module";
    private static final String LEVEL = "level";

    private final Logger logger;
    private final String moduleName;

    public JGradesLogger(Class clazz) {
        this.logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(clazz);
        setCurrentLoggingLevelToLogger(logger);
        this.moduleName = getModuleName(clazz);
    }

    private void setCurrentLoggingLevelToLogger(Logger logger) {
        Level currentLevel = PropertyUtils.getCurrentLoggingLevel();
        logger.setLevel(currentLevel);
    }

    //------------------------------------ TRACE ----------------------------------//
    public void trace(String paramString) {
        setMDC(Level.TRACE);
        logger.trace(paramString);
    }

    public void trace(String paramString, Object paramObject) {
        setMDC(Level.TRACE);
        logger.trace(paramString, paramObject);
    }

    public void trace(String paramString, Object paramObject1, Object paramObject2) {
        setMDC(Level.TRACE);
        logger.trace(paramString, paramObject1, paramObject2);
    }

    public void trace(String paramString, Object... paramVarArgs) {
        setMDC(Level.TRACE);
        logger.trace(paramString, paramVarArgs);
    }

    public void trace(String paramString, Throwable paramThrowable) {
        setMDC(Level.TRACE);
        logger.trace(paramString, paramThrowable);
    }

    public void trace(Marker marker, String paramString) {
        setMDC(Level.TRACE);
        logger.trace(marker, paramString);
    }

    public void trace(Marker marker, String paramString, Object paramObject) {
        setMDC(Level.TRACE);
        logger.trace(marker, paramString, paramObject);
    }

    public void trace(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMDC(Level.TRACE);
        logger.trace(marker, paramString, paramObject1, paramObject2);
    }

    public void trace(Marker marker, String paramString, Object[] paramVarArgs) {
        setMDC(Level.TRACE);
        logger.trace(marker, paramString, paramVarArgs);
    }

    public void trace(Marker marker, String paramString, Throwable paramThrowable) {
        setMDC(Level.TRACE);
        logger.trace(marker, paramString, paramThrowable);
    }

    //------------------------------------ DEBUG ----------------------------------//
    public void debug(String paramString) {
        setMDC(Level.DEBUG);
        logger.debug(paramString);
    }

    public void debug(String paramString, Object paramObject) {
        setMDC(Level.DEBUG);
        logger.debug(paramString, paramObject);
    }

    public void debug(String paramString, Object paramObject1, Object paramObject2) {
        setMDC(Level.DEBUG);
        logger.debug(paramString, paramObject1, paramObject2);
    }

    public void debug(String paramString, Object... paramVarArgs) {
        setMDC(Level.DEBUG);
        logger.debug(paramString, paramVarArgs);
    }

    public void debug(String paramString, Throwable paramThrowable) {
        setMDC(Level.DEBUG);
        logger.debug(paramString, paramThrowable);
    }

    public void debug(Marker marker, String paramString) {
        setMDC(Level.DEBUG);
        logger.debug(marker, paramString);
    }

    public void debug(Marker marker, String paramString, Object paramObject) {
        setMDC(Level.DEBUG);
        logger.debug(marker, paramString, paramObject);
    }

    public void debug(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMDC(Level.DEBUG);
        logger.debug(marker, paramString, paramObject1, paramObject2);
    }

    public void debug(Marker marker, String paramString, Object[] paramVarArgs) {
        setMDC(Level.DEBUG);
        logger.debug(marker, paramString, paramVarArgs);
    }

    public void debug(Marker marker, String paramString, Throwable paramThrowable) {
        setMDC(Level.DEBUG);
        logger.debug(marker, paramString, paramThrowable);
    }

    //------------------------------------ INFO ----------------------------------//
    public void info(String paramString) {
        setMDC(Level.INFO);
        logger.info(paramString);
    }

    public void info(String paramString, Object paramObject) {
        setMDC(Level.INFO);
        logger.info(paramString, paramObject);
    }

    public void info(String paramString, Object paramObject1, Object paramObject2) {
        setMDC(Level.INFO);
        logger.info(paramString, paramObject1, paramObject2);
    }

    public void info(String paramString, Object... paramVarArgs) {
        setMDC(Level.INFO);
        logger.info(paramString, paramVarArgs);
    }

    public void info(String paramString, Throwable paramThrowable) {
        setMDC(Level.INFO);
        logger.info(paramString, paramThrowable);
    }

    public void info(Marker marker, String paramString) {
        setMDC(Level.INFO);
        logger.info(marker, paramString);
    }

    public void info(Marker marker, String paramString, Object paramObject) {
        setMDC(Level.INFO);
        logger.info(marker, paramString, paramObject);
    }

    public void info(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMDC(Level.INFO);
        logger.info(marker, paramString, paramObject1, paramObject2);
    }

    public void info(Marker marker, String paramString, Object... paramVarArgs) {
        setMDC(Level.INFO);
        logger.info(marker, paramString, paramVarArgs);
    }

    public void info(Marker marker, String paramString, Throwable paramThrowable) {
        setMDC(Level.INFO);
        logger.info(marker, paramString, paramThrowable);

    }

    //------------------------------------ WARN ----------------------------------//
    public void warn(String paramString) {
        setMDC(Level.WARN);
        logger.warn(paramString);
    }

    public void warn(String paramString, Object paramObject) {
        setMDC(Level.WARN);
        logger.warn(paramString, paramObject);
    }

    public void warn(String paramString, Object paramObject1, Object paramObject2) {
        setMDC(Level.WARN);
        logger.warn(paramString, paramObject1, paramObject2);
    }

    public void warn(String paramString, Object... paramVarArgs) {
        setMDC(Level.WARN);
        logger.warn(paramString, paramVarArgs);
    }

    public void warn(String paramString, Throwable paramThrowable) {
        setMDC(Level.WARN);
        logger.warn(paramString, paramThrowable);
    }

    public void warn(Marker marker, String paramString) {
        setMDC(Level.WARN);
        logger.warn(marker, paramString);
    }

    public void warn(Marker marker, String paramString, Object paramObject) {
        setMDC(Level.WARN);
        logger.warn(marker, paramString, paramObject);
    }

    public void warn(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMDC(Level.WARN);
        logger.warn(marker, paramString, paramObject1, paramObject2);
    }

    public void warn(Marker marker, String paramString, Object... paramVarArgs) {
        setMDC(Level.WARN);
        logger.warn(marker, paramString, paramVarArgs);
    }

    public void warn(Marker marker, String paramString, Throwable paramThrowable) {
        setMDC(Level.WARN);
        logger.warn(marker, paramString, paramThrowable);
    }

    //------------------------------------ ERROR ----------------------------------//
    public void error(String paramString) {
        setMDC(Level.ERROR);
        logger.error(paramString);
    }

    public void error(String paramString, Object paramObject) {
        setMDC(Level.ERROR);
        logger.error(paramString, paramObject);
    }

    public void error(String paramString, Object paramObject1, Object paramObject2) {
        setMDC(Level.ERROR);
        logger.error(paramString, paramObject1, paramObject2);
    }

    public void error(String paramString, Object... paramVarArgs) {
        setMDC(Level.ERROR);
        logger.error(paramString, paramVarArgs);
    }

    public void error(String paramString, Throwable paramThrowable) {
        setMDC(Level.ERROR);
        logger.error(paramString, paramThrowable);
    }

    public void error(Marker marker, String paramString) {
        setMDC(Level.ERROR);
        logger.error(marker, paramString);
    }

    public void error(Marker marker, String paramString, Object paramObject) {
        setMDC(Level.ERROR);
        logger.error(marker, paramString, paramObject);
    }

    public void error(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMDC(Level.ERROR);
        logger.error(marker, paramString, paramObject1, paramObject2);
    }

    public void error(Marker marker, String paramString, Object... paramVarArgs) {
        setMDC(Level.ERROR);
        logger.error(marker, paramString, paramVarArgs);
    }

    public void error(Marker marker, String paramString, Throwable paramThrowable) {
        setMDC(Level.ERROR);
        logger.error(marker, paramString, paramThrowable);
    }

    private String getModuleName(Class clazz) {
        return StringUtils.split(clazz.getName(), ".")[2];
    }

    private void setMDC(Level loggingLevel) {
        switch (PropertyUtils.getCurrentLoggerConfiguration()) {
            case LOG_PER_TYPE:
                MDC.put(MDC_KEY,loggingLevel.toString().toLowerCase()+SEPARATOR+LEVEL);
                break;
            case LOG_PER_MODULE:
                MDC.put(MDC_KEY, moduleName + SEPARATOR+MODULE);
                break;
            case LOG_PER_TYPE_MODULE:
                String moduleNameWithLoggingLevel = moduleName + SEPARATOR+MODULE+SEPARATOR+ loggingLevel.toString().toLowerCase()+
                        SEPARATOR+LEVEL;
                MDC.put(MDC_KEY, moduleNameWithLoggingLevel);
                break;
        }
    }
}
