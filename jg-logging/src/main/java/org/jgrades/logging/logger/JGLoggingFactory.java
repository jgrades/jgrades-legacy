package org.jgrades.logging.logger;

public class JGLoggingFactory {

    private JGLoggingFactory() {
    }

    public static JGradesLogger getLogger(Class clazz) {
        return new JGradesLogger(clazz);
    }
}
