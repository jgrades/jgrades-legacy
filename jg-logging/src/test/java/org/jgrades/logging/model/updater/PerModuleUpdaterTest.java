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

import org.jgrades.logging.utils.LogbackXmlEditor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.jgrades.logging.utils.InternalProperties.LOGS_DIRECTORY;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PerModuleUpdaterTest {
    @Mock
    private LogbackXmlEditor xmlEditorMock;

    @InjectMocks
    private PerModuleUpdater perModuleUpdater;

    @Test
    public void shouldSetFileNameWithModule_whenNodeListHandled() throws Exception {
        // given
        NodeList nodeList = mock(NodeList.class);
        when(nodeList.getLength()).thenReturn(3);

        Node node0 = mock(Node.class);
        Node node1 = mock(Node.class);
        Node node2 = mock(Node.class);
        when(nodeList.item(eq(0))).thenReturn(node0);
        when(nodeList.item(eq(1))).thenReturn(node1);
        when(nodeList.item(eq(2))).thenReturn(node2);

        when(xmlEditorMock.getFileNamePatternNodes()).thenReturn(nodeList);

        when(xmlEditorMock.getLogTypeFromAppenderName(eq(node0))).thenReturn("external");
        when(xmlEditorMock.getLogTypeFromAppenderName(eq(node1))).thenReturn("internal");
        when(xmlEditorMock.getLogTypeFromAppenderName(eq(node2))).thenReturn("internal");

        // when
        perModuleUpdater.updateFileNameTags();

        // then
        verify(node0, times(1)).setTextContent(LOGS_DIRECTORY + "/jg_external-lib_%d{yyyy-MM-dd}_%i.log");
        verify(node1, times(1)).setTextContent(LOGS_DIRECTORY + "/jg_${module-name-placeholder}_%d{yyyy-MM-dd}_%i.log");
        verify(node2, times(1)).setTextContent(LOGS_DIRECTORY + "/jg_${module-name-placeholder}_%d{yyyy-MM-dd}_%i.log");
    }

}
