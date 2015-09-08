package org.jgrades.monitor.dependency;


import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;

import java.io.File;

public class FileChecker implements DependencyChecker {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(FileChecker.class);

    private String path;

    public FileChecker(String path) {
        this.path = path;
    }

    @Override
    public boolean check() {
        boolean result = new File(path).exists();
        if (!result) {
            LOGGER.error("FILE {} IS MISSING, BUT IT IS A MANDATORY DEPENDENCY OF JGRADES SYSTEM", path);
        }
        return result;
    }
}
