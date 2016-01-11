/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.logging.model.updater;

import org.jgrades.logging.model.XmlFileNameTagsUpdater;
import org.jgrades.logging.utils.LogbackXmlEditor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.jgrades.logging.utils.InternalProperties.LOGS_DIRECTORY;

public class PerLevelUpdater implements XmlFileNameTagsUpdater {
    private LogbackXmlEditor xmlEditor = new LogbackXmlEditor();

    @Override
    public void updateFileNameTags() {
        NodeList nodeList = xmlEditor.getFileNamePatternNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String levelName = xmlEditor.getLevelNameFromAppenderName(node);
            node.setTextContent(LOGS_DIRECTORY + "/jg_" + levelName + "_%d{yyyy-MM-dd}_%i.log");
        }
    }
}
