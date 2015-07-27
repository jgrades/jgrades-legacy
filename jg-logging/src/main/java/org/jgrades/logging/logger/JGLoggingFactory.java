package org.jgrades.logging.logger;

public final class JGLoggingFactory {
    private JGLoggingFactory() {

    }

    public static JGradesLogger getLogger(Class clazz) {
        return new JGradesLogger(clazz);
    }
}
