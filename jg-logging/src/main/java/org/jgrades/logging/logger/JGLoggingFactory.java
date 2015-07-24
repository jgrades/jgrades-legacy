package org.jgrades.logging.logger;

import org.apache.commons.lang3.StringUtils;
import org.jgrades.logging.logger.configuration.LoggingConfiguration;
import org.jgrades.logging.logger.utils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import sun.rmi.runtime.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.jgrades.logging.logger.configuration.LoggingConfiguration.LOG_PER_TYPE;

public class JGLoggingFactory {

    public static JGradesLogger getLogger(Class clazz) throws IOException {
        return new JGradesLogger(clazz);
    }

    public static void main(String ... strings) throws IOException {
        JGradesLogger logger = getLogger(JGLoggingFactory.class);

        logger.info("JGLoggingFactroy class");


    }


}
