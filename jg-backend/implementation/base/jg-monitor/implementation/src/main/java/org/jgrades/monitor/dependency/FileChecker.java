/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

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
