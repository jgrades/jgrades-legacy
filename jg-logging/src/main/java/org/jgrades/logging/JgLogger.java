/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging;

import org.jgrades.logging.utils.ModuleNameResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Marker;

public class JgLogger {
    private static final String MODULE_NAME_PLACEHOLDER = "module-name-placeholder";

    private final Logger logger; //NOSONAR
    private final String moduleName;

    public JgLogger(Class clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
        this.moduleName = ModuleNameResolver.resolve(clazz.getCanonicalName());
        setMdcModuleName();
    }

    private static void removeMdcModuleName() {
        MDC.remove(MODULE_NAME_PLACEHOLDER);
    }

    private void setMdcModuleName() {
        MDC.put(MODULE_NAME_PLACEHOLDER, moduleName);
    }

    //------------------------------------ TRACE ----------------------------------//
    public void trace(String paramString) {
        setMdcModuleName();
        logger.trace(paramString);
        removeMdcModuleName();
    }

    public void trace(String paramString, Object paramObject) {
        setMdcModuleName();
        logger.trace(paramString, paramObject);
        removeMdcModuleName();
    }

    public void trace(String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.trace(paramString, paramObject1, paramObject2);
        removeMdcModuleName();
    }

    public void trace(String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.trace(paramString, paramVarArgs);
        removeMdcModuleName();
    }

    public void trace(String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.trace(paramString, paramThrowable);
        removeMdcModuleName();
    }

    public void trace(Marker marker, String paramString) {
        setMdcModuleName();
        logger.trace(marker, paramString);
        removeMdcModuleName();
    }

    public void trace(Marker marker, String paramString, Object paramObject) {
        setMdcModuleName();
        logger.trace(marker, paramString, paramObject);
        removeMdcModuleName();
    }

    public void trace(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.trace(marker, paramString, paramObject1, paramObject2);
        removeMdcModuleName();
    }

    public void trace(Marker marker, String paramString, Object[] paramVarArgs) {
        setMdcModuleName();
        logger.trace(marker, paramString, paramVarArgs);
        removeMdcModuleName();
    }

    public void trace(Marker marker, String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.trace(marker, paramString, paramThrowable);
        removeMdcModuleName();
    }

    //------------------------------------ DEBUG ----------------------------------//
    public void debug(String paramString) {
        setMdcModuleName();
        logger.debug(paramString);
        removeMdcModuleName();
    }

    public void debug(String paramString, Object paramObject) {
        setMdcModuleName();
        logger.debug(paramString, paramObject);
        removeMdcModuleName();
    }

    public void debug(String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.debug(paramString, paramObject1, paramObject2);
        removeMdcModuleName();
    }

    public void debug(String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.debug(paramString, paramVarArgs);
        removeMdcModuleName();
    }

    public void debug(String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.debug(paramString, paramThrowable);
        removeMdcModuleName();
    }

    public void debug(Marker marker, String paramString) {
        setMdcModuleName();
        logger.debug(marker, paramString);
        removeMdcModuleName();
    }

    public void debug(Marker marker, String paramString, Object paramObject) {
        setMdcModuleName();
        logger.debug(marker, paramString, paramObject);
        removeMdcModuleName();
    }

    public void debug(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.debug(marker, paramString, paramObject1, paramObject2);
        removeMdcModuleName();
    }

    public void debug(Marker marker, String paramString, Object[] paramVarArgs) {
        setMdcModuleName();
        logger.debug(marker, paramString, paramVarArgs);
        removeMdcModuleName();
    }

    public void debug(Marker marker, String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.debug(marker, paramString, paramThrowable);
        removeMdcModuleName();
    }

    //------------------------------------ INFO ----------------------------------//
    public void info(String paramString) {
        setMdcModuleName();
        logger.info(paramString);
        removeMdcModuleName();
    }

    public void info(String paramString, Object paramObject) {
        setMdcModuleName();
        logger.info(paramString, paramObject);
        removeMdcModuleName();
    }

    public void info(String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.info(paramString, paramObject1, paramObject2);
        removeMdcModuleName();
    }

    public void info(String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.info(paramString, paramVarArgs);
        removeMdcModuleName();
    }

    public void info(String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.info(paramString, paramThrowable);
        removeMdcModuleName();
    }

    public void info(Marker marker, String paramString) {
        setMdcModuleName();
        logger.info(marker, paramString);
        removeMdcModuleName();
    }

    public void info(Marker marker, String paramString, Object paramObject) {
        setMdcModuleName();
        logger.info(marker, paramString, paramObject);
        removeMdcModuleName();
    }

    public void info(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.info(marker, paramString, paramObject1, paramObject2);
        removeMdcModuleName();
    }

    public void info(Marker marker, String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.info(marker, paramString, paramVarArgs);
        removeMdcModuleName();
    }

    public void info(Marker marker, String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.info(marker, paramString, paramThrowable);
        removeMdcModuleName();
    }

    //------------------------------------ WARN ----------------------------------//
    public void warn(String paramString) {
        setMdcModuleName();
        logger.warn(paramString);
        removeMdcModuleName();
    }

    public void warn(String paramString, Object paramObject) {
        setMdcModuleName();
        logger.warn(paramString, paramObject);
        removeMdcModuleName();
    }

    public void warn(String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.warn(paramString, paramObject1, paramObject2);
        removeMdcModuleName();
    }

    public void warn(String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.warn(paramString, paramVarArgs);
        removeMdcModuleName();
    }

    public void warn(String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.warn(paramString, paramThrowable);
        removeMdcModuleName();
    }

    public void warn(Marker marker, String paramString) {
        setMdcModuleName();
        logger.warn(marker, paramString);
        removeMdcModuleName();
    }

    public void warn(Marker marker, String paramString, Object paramObject) {
        setMdcModuleName();
        logger.warn(marker, paramString, paramObject);
        removeMdcModuleName();
    }

    public void warn(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.warn(marker, paramString, paramObject1, paramObject2);
        removeMdcModuleName();
    }

    public void warn(Marker marker, String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.warn(marker, paramString, paramVarArgs);
        removeMdcModuleName();
    }

    public void warn(Marker marker, String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.warn(marker, paramString, paramThrowable);
        removeMdcModuleName();
    }

    //------------------------------------ ERROR ----------------------------------//
    public void error(String paramString) {
        setMdcModuleName();
        logger.error(paramString);
        removeMdcModuleName();
    }

    public void error(String paramString, Object paramObject) {
        setMdcModuleName();
        logger.error(paramString, paramObject);
        removeMdcModuleName();
    }

    public void error(String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.error(paramString, paramObject1, paramObject2);
        removeMdcModuleName();
    }

    public void error(String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.error(paramString, paramVarArgs);
        removeMdcModuleName();
    }

    public void error(String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.error(paramString, paramThrowable);
        removeMdcModuleName();
    }

    public void error(Marker marker, String paramString) {
        setMdcModuleName();
        logger.error(marker, paramString);
        removeMdcModuleName();
    }

    public void error(Marker marker, String paramString, Object paramObject) {
        setMdcModuleName();
        logger.error(marker, paramString, paramObject);
        removeMdcModuleName();
    }

    public void error(Marker marker, String paramString, Object paramObject1, Object paramObject2) {
        setMdcModuleName();
        logger.error(marker, paramString, paramObject1, paramObject2);
        removeMdcModuleName();
    }

    public void error(Marker marker, String paramString, Object... paramVarArgs) {
        setMdcModuleName();
        logger.error(marker, paramString, paramVarArgs);
        removeMdcModuleName();
    }

    public void error(Marker marker, String paramString, Throwable paramThrowable) {
        setMdcModuleName();
        logger.error(marker, paramString, paramThrowable);
        removeMdcModuleName();
    }
}
