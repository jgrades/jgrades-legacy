package org.jgrades.logging.logger;

import org.slf4j.Logger;

public final class JGLoggingFactory {

    private JGLoggingFactory() {

    }

    public static JGradesLogger getLogger(Class clazz) {
        return new JGradesLogger(clazz);
    }

    public static void main(String ... arrgs) {

        JGradesLogger logger = JGLoggingFactory.getLogger(JGLoggingFactory.class);

        logger.error("Test log 3");

        logger.info("Test log");

        logger.trace("Test log 4");

       // logger.info("Test log2");
    }
}
