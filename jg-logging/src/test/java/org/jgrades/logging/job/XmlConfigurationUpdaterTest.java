package org.jgrades.logging.job;

import ch.qos.logback.classic.Level;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.model.LoggingStrategy;
import org.jgrades.logging.utils.LogbackXmlEditor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class XmlConfigurationUpdaterTest {
    @Mock
    private LogbackXmlEditor xmlEditorMock;

    @Mock
    private LoggerContextReloader reloaderMock;

    @InjectMocks
    private XmlConfigurationUpdater xmlConfigurationUpdater;


    @Test
    public void shouldOnlyReloadContext_whenXmlDoesntExists() throws Exception {
        // given
        when(xmlEditorMock.isXmlExists()).thenReturn(false);

        // when
        xmlConfigurationUpdater.update(new LoggingConfiguration());

        // then
        verify(reloaderMock).reload();
        verify(xmlEditorMock).isXmlExists();
        verifyNoMoreInteractions(xmlEditorMock);
    }

    @Test
    public void shouldReloadContextAndEditXml_whenXmlExists() throws Exception {
        // given
        when(xmlEditorMock.isXmlExists()).thenReturn(true);

        LoggingConfiguration conf = new LoggingConfiguration();
        conf.setLevel(Level.DEBUG);
        conf.setLoggingStrategy(LoggingStrategy.LOG_FILE_PER_LEVEL);
        conf.setMaxDays(7);
        conf.setMaxFileSize("100KB");

        Node levelNode = mock(Node.class);
        when(xmlEditorMock.getLevelNode()).thenReturn(levelNode);

        NodeList maxDaysNodeList = mock(NodeList.class);
        when(maxDaysNodeList.getLength()).thenReturn(1);
        Node maxDayNode = mock(Node.class);
        when(maxDaysNodeList.item(eq(0))).thenReturn(maxDayNode);
        when(xmlEditorMock.getMaxDays()).thenReturn(maxDaysNodeList);

        NodeList maxFileSizeNodeList = mock(NodeList.class);
        when(maxFileSizeNodeList.getLength()).thenReturn(1);
        Node maxFileSizeNode = mock(Node.class);
        when(maxFileSizeNodeList.item(eq(0))).thenReturn(maxFileSizeNode);
        when(xmlEditorMock.getMaxFileSizeNodes()).thenReturn(maxFileSizeNodeList);

        // when
        xmlConfigurationUpdater.update(conf);

        // then
        verify(reloaderMock).reload();
        verify(xmlEditorMock).isXmlExists();

        verify(levelNode).setTextContent("DEBUG");
        verify(maxDayNode).setTextContent("7");
        verify(maxFileSizeNode).setTextContent("100KB");
    }
}
