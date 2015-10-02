/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.utils;

import org.apache.commons.lang3.StringUtils;

public final class ModuleNameResolver {
    private static final String EXTERNAL_LIB = "external-lib";

    private ModuleNameResolver() {
    }

    public static String resolve(String canonicalClassName) {
        if (StringUtils.isEmpty(canonicalClassName)) {
            return EXTERNAL_LIB;
        } else if (canonicalClassName.startsWith("org.jgrades")) {
            String moduleName = StringUtils.split(canonicalClassName, ".")[2];
            if (StringUtils.isNotEmpty(moduleName)) {
                return moduleName;
            }
        }
        return EXTERNAL_LIB;
    }
}
