package org.jgrades.logging.job;

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.model.LoggingStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobExecutionContext;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogConfigurationMonitorTest {
    @Mock
    private LoggingConfigurationDao configurationDaoMock;

    @Mock
    private XmlConfigurationUpdater configurationUpdaterMock;

    @Mock
    private LoggerContextReloader contextReloaderMock;

    @InjectMocks
    private LogConfigurationMonitor logConfigurationMonitor;

    @Test
    public void shouldProceedNewConfiguration_whenCachedIsNull() throws Exception {
        // given
        ReflectionTestUtils.setField(logConfigurationMonitor, "cachedConfig", null);
        LoggingConfiguration configuration = new LoggingConfiguration();
        when(configurationDaoMock.getCurrentConfiguration()).thenReturn(configuration);

        // when
        logConfigurationMonitor.execute(mock(JobExecutionContext.class));

        // then
        isProcessNewConfigInvoked(true, null);
    }

    @Test
    public void shouldProceedNewConfiguration_whenCachedIsDifferentThanNew() throws Exception {
        // given
        LoggingConfiguration cachedConfiguration = new LoggingConfiguration();
        cachedConfiguration.setLoggingStrategy(LoggingStrategy.LOG_FILE_PER_LEVEL);
        ReflectionTestUtils.setField(logConfigurationMonitor, "cachedConfig", cachedConfiguration);

        LoggingConfiguration newConfiguration = new LoggingConfiguration();
        newConfiguration.setLoggingStrategy(LoggingStrategy.LOG_FILE_PER_MODULE);
        when(configurationDaoMock.getCurrentConfiguration()).thenReturn(newConfiguration);

        // when
        logConfigurationMonitor.execute(mock(JobExecutionContext.class));

        // then
        isProcessNewConfigInvoked(true, cachedConfiguration);
    }

    @Test
    public void shouldNotProceedNewConfiguration_whenCachedIsEqualToNew() throws Exception {
        // given
        LoggingConfiguration cachedConfiguration = new LoggingConfiguration();
        cachedConfiguration.setLoggingStrategy(LoggingStrategy.LOG_FILE_PER_LEVEL);
        ReflectionTestUtils.setField(logConfigurationMonitor, "cachedConfig", cachedConfiguration);

        LoggingConfiguration newConfiguration = new LoggingConfiguration();
        newConfiguration.setLoggingStrategy(LoggingStrategy.LOG_FILE_PER_LEVEL);
        when(configurationDaoMock.getCurrentConfiguration()).thenReturn(newConfiguration);

        // when
        logConfigurationMonitor.execute(mock(JobExecutionContext.class));

        // then
        isProcessNewConfigInvoked(false, cachedConfiguration);
    }

    private void isProcessNewConfigInvoked(boolean processingShouldBeDone, LoggingConfiguration oldCacheConfig) {
        int expectedTimes = processingShouldBeDone ? 1 : 0;
        verify(configurationUpdaterMock, times(expectedTimes)).update(any(LoggingConfiguration.class));
        verify(contextReloaderMock, times(expectedTimes)).reload();

        LoggingConfiguration cachedConfig = (LoggingConfiguration) ReflectionTestUtils.getField(logConfigurationMonitor, "cachedConfig");
        if (processingShouldBeDone) {
            assertThat(oldCacheConfig).isNotEqualTo(cachedConfig);
        } else {
            assertThat(oldCacheConfig).isEqualTo(cachedConfig);
        }
    }
}
