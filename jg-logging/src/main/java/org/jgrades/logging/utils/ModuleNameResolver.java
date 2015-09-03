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
