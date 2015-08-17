package org.jgrades.logging;

import org.jgrades.logging.utils.ModuleNameResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;

public class JgLogger {
    private static final String MODULE_NAME_PLACEHOLDER = "module-name-placeholder";

    private final Logger logger;
    private final String moduleName;

    public JgLogger(Class clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
        this.moduleName = ModuleNameResolver.resolve(clazz.getCanonicalName());
        setMdcModuleName();
    }

    private void setMdcModuleName() {
        MDC.put(MODULE_NAME_PLACEHOLDER, this.moduleName);
    }

    //------------------------------------ TRACE ----------------------------------//
    public void trace(String paramString) {
        setMdcModuleName();
        logger.trace(paramString);
    }

    public void trace(String paramString, Object paramObject) {
        setMdcModuleName();
        logger.trace(paramString, paramObject);
    }

    public void trace(String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.trace(paramString, paramObject1, paramObject2);
    }

    public void trace(String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.trace(paramString, paramVarArgs);
    }

    public void trace(String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.trace(paramString, paramThrowable);
    }

    public void trace(Marker marker, String paramString) {
        setMdcModuleName();
        logger.trace(marker, paramString);
    }

    public void trace(Marker marker, String paramString, Object paramObject) {
        setMdcModuleName();
        logger.trace(marker, paramString, paramObject);
    }

    public void trace(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.trace(marker, paramString, paramObject1, paramObject2);
    }

    public void trace(Marker marker, String paramString, Object[] paramVarArgs) {
        setMdcModuleName();
        logger.trace(marker, paramString, paramVarArgs);
    }

    public void trace(Marker marker, String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.trace(marker, paramString, paramThrowable);
    }

    //------------------------------------ DEBUG ----------------------------------//
    public void debug(String paramString) {
        setMdcModuleName();
        logger.debug(paramString);
    }

    public void debug(String paramString, Object paramObject) {
        setMdcModuleName();
        logger.debug(paramString, paramObject);
    }

    public void debug(String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.debug(paramString, paramObject1, paramObject2);
    }

    public void debug(String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.debug(paramString, paramVarArgs);
    }

    public void debug(String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.debug(paramString, paramThrowable);
    }

    public void debug(Marker marker, String paramString) {
        setMdcModuleName();
        logger.debug(marker, paramString);
    }

    public void debug(Marker marker, String paramString, Object paramObject) {
        setMdcModuleName();
        logger.debug(marker, paramString, paramObject);
    }

    public void debug(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.debug(marker, paramString, paramObject1, paramObject2);
    }

    public void debug(Marker marker, String paramString, Object[] paramVarArgs) {
        setMdcModuleName();
        logger.debug(marker, paramString, paramVarArgs);
    }

    public void debug(Marker marker, String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.debug(marker, paramString, paramThrowable);
    }

    //------------------------------------ INFO ----------------------------------//
    public void info(String paramString) {
        setMdcModuleName();
        logger.info(paramString);
    }

    public void info(String paramString, Object paramObject) {
        setMdcModuleName();
        logger.info(paramString, paramObject);
    }

    public void info(String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.info(paramString, paramObject1, paramObject2);
    }

    public void info(String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.info(paramString, paramVarArgs);
    }

    public void info(String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.info(paramString, paramThrowable);
    }

    public void info(Marker marker, String paramString) {
        setMdcModuleName();
        logger.info(marker, paramString);
    }

    public void info(Marker marker, String paramString, Object paramObject) {
        setMdcModuleName();
        logger.info(marker, paramString, paramObject);
    }

    public void info(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.info(marker, paramString, paramObject1, paramObject2);
    }

    public void info(Marker marker, String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.info(marker, paramString, paramVarArgs);
    }

    public void info(Marker marker, String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.info(marker, paramString, paramThrowable);

    }

    //------------------------------------ WARN ----------------------------------//
    public void warn(String paramString) {
        setMdcModuleName();
        logger.warn(paramString);
    }

    public void warn(String paramString, Object paramObject) {
        setMdcModuleName();
        logger.warn(paramString, paramObject);
    }

    public void warn(String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.warn(paramString, paramObject1, paramObject2);
    }

    public void warn(String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.warn(paramString, paramVarArgs);
    }

    public void warn(String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.warn(paramString, paramThrowable);
    }

    public void warn(Marker marker, String paramString) {
        logger.warn(marker, paramString);
    }

    public void warn(Marker marker, String paramString, Object paramObject) {
        setMdcModuleName();
        logger.warn(marker, paramString, paramObject);
    }

    public void warn(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.warn(marker, paramString, paramObject1, paramObject2);
    }

    public void warn(Marker marker, String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.warn(marker, paramString, paramVarArgs);
    }

    public void warn(Marker marker, String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.warn(marker, paramString, paramThrowable);
    }

    //------------------------------------ ERROR ----------------------------------//
    public void error(String paramString) {
        setMdcModuleName();
        logger.error(paramString);
    }

    public void error(String paramString, Object paramObject) {
        setMdcModuleName();
        logger.error(paramString, paramObject);
    }

    public void error(String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.error(paramString, paramObject1, paramObject2);
    }

    public void error(String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.error(paramString, paramVarArgs);
    }

    public void error(String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.error(paramString, paramThrowable);
    }

    public void error(Marker marker, String paramString) {
        setMdcModuleName();
        logger.error(marker, paramString);
    }

    public void error(Marker marker, String paramString, Object paramObject) {
        setMdcModuleName();
        logger.error(marker, paramString, paramObject);
    }

    public void error(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.error(marker, paramString, paramObject1, paramObject2);
    }

    public void error(Marker marker, String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.error(marker, paramString, paramVarArgs);
    }

    public void error(Marker marker, String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.error(marker, paramString, paramThrowable);
    }
}
