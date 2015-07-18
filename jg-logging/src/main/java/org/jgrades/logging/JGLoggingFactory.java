package org.jgrades.logging;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.NOPLoggerFactory;
import org.slf4j.helpers.SubstituteLoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

/**
 * Created by Piotr on 2015-07-15.
 */
public class JGLoggingFactory {

    public static Logger getLogger(Class clazz) {
        return LoggerFactory.getLogger("logger");
    }

}
