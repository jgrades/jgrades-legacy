package org.jgrades.logging.service;

import ch.qos.logback.classic.Level;
import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.model.LoggingStrategy;
import org.jgrades.logging.utils.LogbackXmlEditor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoggingServiceImplTest {
    @Mock
    private LogbackXmlEditor xmlEditorMock;

    @Mock
    private LoggingConfigurationDao daoMock;

    @InjectMocks
    private LoggingServiceImpl loggingService;

    @Test
    public void shouldGetConfigurationFromDao_whenGettingConfigurationInvoked() throws Exception {
        // when
        loggingService.getLoggingConfiguration();

        // then
        Mockito.verify(daoMock, times(1)).getCurrentConfiguration();
        Mockito.verifyNoMoreInteractions(xmlEditorMock, daoMock);
    }

    @Test
    public void shouldSetConfigurationWithDao_whenSettingConfigurationInvoked() throws Exception {
        // given
        LoggingConfiguration conf = new LoggingConfiguration();

        // when
        loggingService.setLoggingConfiguration(conf);

        // then
        Mockito.verify(daoMock, times(1)).setConfiguration(conf);
        Mockito.verifyNoMoreInteractions(xmlEditorMock, daoMock);
    }

    @Test
    public void shouldUsingDefaultConf_whenXmlDoesntExistsAndCurrentConfIsEqualToDefault() throws Exception {
        // given
        LoggingConfiguration conf = new LoggingConfiguration();
        when(xmlEditorMock.isXmlExists()).thenReturn(false);
        when(daoMock.getCurrentConfiguration()).thenReturn(conf);
        when(daoMock.getDefaultConfiguration()).thenReturn(conf);

        // when
        boolean usingDefaultConfiguration = loggingService.isUsingDefaultConfiguration();

        // then
        assertThat(usingDefaultConfiguration).isTrue();
    }

    @Test
    public void shouldUsingDefaultConf_whenXmlDoesntExistsAndCurrentConfIsNotEqualToDefault() throws Exception {
        // given
        LoggingConfiguration conf = new LoggingConfiguration(LoggingStrategy.LOG_FILE_PER_LEVEL, Level.INFO, "10KB", 7);
        LoggingConfiguration defaultConf = new LoggingConfiguration();

        when(xmlEditorMock.isXmlExists()).thenReturn(false);
        when(daoMock.getCurrentConfiguration()).thenReturn(conf);
        when(daoMock.getDefaultConfiguration()).thenReturn(defaultConf);

        // when
        boolean usingDefaultConfiguration = loggingService.isUsingDefaultConfiguration();

        // then
        assertThat(usingDefaultConfiguration).isTrue();
    }

    @Test
    public void shouldUsingDefaultConf_whenXmlExistsAndCurrentConfIsEqualToDefault() throws Exception {
        // given
        LoggingConfiguration conf = new LoggingConfiguration();
        when(xmlEditorMock.isXmlExists()).thenReturn(true);
        when(daoMock.getCurrentConfiguration()).thenReturn(conf);
        when(daoMock.getDefaultConfiguration()).thenReturn(conf);

        // when
        boolean usingDefaultConfiguration = loggingService.isUsingDefaultConfiguration();

        // then
        assertThat(usingDefaultConfiguration).isTrue();
    }

    @Test
    public void shouldNotUsingDefaultConf_whenXmlExistsAndCurrentConfIsNotEqualToDefault() throws Exception {
        // given
        LoggingConfiguration conf = new LoggingConfiguration(LoggingStrategy.LOG_FILE_PER_LEVEL, Level.INFO, "10KB", 7);
        LoggingConfiguration defaultConf = new LoggingConfiguration();

        when(xmlEditorMock.isXmlExists()).thenReturn(true);
        when(daoMock.getCurrentConfiguration()).thenReturn(conf);
        when(daoMock.getDefaultConfiguration()).thenReturn(defaultConf);

        // when
        boolean usingDefaultConfiguration = loggingService.isUsingDefaultConfiguration();

        // then
        assertThat(usingDefaultConfiguration).isFalse();
    }
}
