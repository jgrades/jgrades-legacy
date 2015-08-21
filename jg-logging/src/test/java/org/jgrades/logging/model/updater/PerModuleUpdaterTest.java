package org.jgrades.logging.model.updater;

import org.jgrades.logging.utils.LogbackXmlEditor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
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

        when(xmlEditorMock.getLevelNameFromAppenderName(eq(node0))).thenReturn("info");
        when(xmlEditorMock.getLevelNameFromAppenderName(eq(node1))).thenReturn("warn");
        when(xmlEditorMock.getLevelNameFromAppenderName(eq(node2))).thenReturn("error");

        when(node0.getTextContent()).thenReturn("external-lib");
        when(node1.getTextContent()).thenReturn("lic");
        when(node2.getTextContent()).thenReturn("rest");

        // when
        perModuleUpdater.updateFileNameTags();

        // then
        verify(node0, times(1)).setTextContent(LOGS_DIRECTORY + "/jg_external-lib_%d{yyyy-MM-dd}_%i.log");
        verify(node1, times(1)).setTextContent(LOGS_DIRECTORY + "/jg_${module-name-placeholder}_%d{yyyy-MM-dd}_%i.log");
        verify(node2, times(1)).setTextContent(LOGS_DIRECTORY + "/jg_${module-name-placeholder}_%d{yyyy-MM-dd}_%i.log");
    }

}
