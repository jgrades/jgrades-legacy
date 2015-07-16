package org.jgrades.lic.app.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.lic.api.model.LicenceProperty;

import java.util.List;

class PropertiesTextAreaParser {
    private static final String ERROR_PARSING_MESSAGE = "Invalid property definition appears in line: ";

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

    private void processLine(List<LicenceProperty> result, String line) {
        String[] property = line.split("=", 2);
        if (property.length < 2) {
            throw new IllegalArgumentException(ERROR_PARSING_MESSAGE + line);
        }
        LicenceProperty licenceProperty = new LicenceProperty();
        licenceProperty.setName(property[0]);
        licenceProperty.setValue(property[1]);
        result.add(licenceProperty);
    }
}
