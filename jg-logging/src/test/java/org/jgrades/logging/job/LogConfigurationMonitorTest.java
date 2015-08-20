package org.jgrades.logging.job;

import org.jgrades.logging.dao.LoggingConfigurationDao;
import org.jgrades.logging.model.LoggingConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    public void testName() throws Exception {
        //TODO
    }

    private void isProcessNewConfigInvoked(boolean expectedResult, LoggingConfiguration oldCacheConfig) {
        int expectedTimes = expectedResult ? 1 : 0;
        verify(configurationUpdaterMock, times(expectedTimes)).update(any(LoggingConfiguration.class));
        assertThat(oldCacheConfig).isNotEqualTo((LoggingConfiguration) ReflectionTestUtils.getField(logConfigurationMonitor, "cachedConfig"));
        verify(contextReloaderMock, times(expectedTimes)).reload();
    }
}
