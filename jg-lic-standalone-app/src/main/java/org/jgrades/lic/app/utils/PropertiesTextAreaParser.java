/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.app.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.lic.api.model.LicenceProperty;

import java.util.List;
import java.util.Optional;

public class PropertiesTextAreaParser {
    private static final String ERROR_PARSING_MESSAGE = "Invalid property definition appears in line: ";

    private static void processLine(List<LicenceProperty> result, String line) {
        String[] property = line.split("=", 2);
        if (property.length < 2) {
            throw new IllegalArgumentException(ERROR_PARSING_MESSAGE + line);
        }
        LicenceProperty licenceProperty = new LicenceProperty();
        licenceProperty.setName(property[0]);
        licenceProperty.setValue(property[1]);
        result.add(licenceProperty);
    }

    public List<LicenceProperty> getProperties(String textAreaContent) {
        List<LicenceProperty> result = Lists.newArrayList();

        if (StringUtils.isEmpty(textAreaContent)) {
            return result;
        }

        String[] propertyLines = textAreaContent.split("[\\r\\n]+");
        for (String propertyLine : propertyLines) {
            if (propertyLine.isEmpty()) {
                continue;
            }
            processLine(result, propertyLine);
        }

        return result;
    }

    public String getPropertiesText(List<LicenceProperty> properties) {
        if (!Optional.ofNullable(properties).isPresent()) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (LicenceProperty property : properties) {
            sb.append(property.getName());
            sb.append("=");
            sb.append(property.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }
}
