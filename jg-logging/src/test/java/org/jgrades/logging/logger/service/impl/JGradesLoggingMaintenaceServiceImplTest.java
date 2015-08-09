package org.jgrades.logging.logger.service.impl;


import ch.qos.logback.classic.Level;
import org.jgrades.logging.logger.configuration.LoggingConfiguration;
import org.jgrades.logging.logger.utils.PropertyUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGradesLoggingMaintenaceServiceImplTest {

    private JGradesLoggingMaintenaceServiceImpl jGradesLoggingMaintenaceService;

    @Before
    public void init(){
        jGradesLoggingMaintenaceService = new JGradesLoggingMaintenaceServiceImpl();
    }

    @After
    public void clean(){
        jGradesLoggingMaintenaceService.setLevel(Level.INFO);
        jGradesLoggingMaintenaceService.setLoggingMode(LoggingConfiguration.LOG_PER_TYPE_MODULE);
    }

    @Test
    public void setNewLevelServiceTest(){
        jGradesLoggingMaintenaceService.setLevel(Level.ERROR);

        assertThat(PropertyUtils.getCurrentLoggingLevel()).isEqualTo(Level.ERROR);
    }

    @Test
    public void setNewMaxFileSizeServiceTest(){
        jGradesLoggingMaintenaceService.setMaxSize("1024 MB");

        assertThat(jGradesLoggingMaintenaceService.getParser().getElementLogFileSize()).isEqualTo(1024);
    }

    @Test
    public void setNewCleaningTimeServiceTest(){
        jGradesLoggingMaintenaceService.setCleaningAfterDays(1024);

        assertThat(jGradesLoggingMaintenaceService.getParser().getElementLogStorageTimeLimit()).isEqualTo(1024);
    }

    @Test
    public void getCurrentLoggingConfigurationServiceTest(){

        assertThat(jGradesLoggingMaintenaceService.getLoggingConfiguration()).isEqualTo(LoggingConfiguration.LOG_PER_TYPE_MODULE);
    }

    @Test
    public void setNewLoggingConfigurationSerbviceTest(){
        jGradesLoggingMaintenaceService.setLoggingMode(LoggingConfiguration.LOG_PER_TYPE);

        assertThat(jGradesLoggingMaintenaceService.getLoggingConfiguration()).isEqualTo(LoggingConfiguration.LOG_PER_TYPE);
    }
}
