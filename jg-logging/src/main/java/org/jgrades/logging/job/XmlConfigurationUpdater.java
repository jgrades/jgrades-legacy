/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.job;

import ch.qos.logback.classic.Level;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.model.LoggingStrategy;
import org.jgrades.logging.utils.LogbackXmlEditor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlConfigurationUpdater {
    private LogbackXmlEditor xmlEditor = new LogbackXmlEditor();
    private LoggerContextReloader reloader = new LoggerContextReloader();

    public void update(LoggingConfiguration targetConfig) {
        if (xmlEditor.isXmlExists()) {
            updateFileNames(targetConfig.getLoggingStrategy());
            updateLevel(Level.toLevel(targetConfig.getLevel().toString()));
            updateMaxFileSize(targetConfig.getMaxFileSize());
            updateMaxDays(targetConfig.getMaxDays());
            xmlEditor.saveWithChanges();
        }
        reloader.reload();
    }

    private void updateFileNames(LoggingStrategy loggingStrategy) {
        loggingStrategy.getUpdater().updateFileNameTags();
    }

    private void updateLevel(Level level) {
        Node node = xmlEditor.getLevelNode();
        node.setTextContent(level.toString().toUpperCase());
    }

    private void updateMaxFileSize(String maxFileSize) {
        NodeList nodeList = xmlEditor.getMaxFileSizeNodes();
        updateNodesWithValue(nodeList, maxFileSize);
    }

    private void updateMaxDays(Integer maxDays) {
        NodeList nodeList = xmlEditor.getMaxDays();
        updateNodesWithValue(nodeList, maxDays.toString());
    }

    private void updateNodesWithValue(NodeList nodeList, String newValue) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            nodeList.item(i).setTextContent(newValue);
        }
    }
}
