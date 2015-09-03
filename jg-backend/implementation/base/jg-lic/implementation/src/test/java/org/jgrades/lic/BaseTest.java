package org.jgrades.lic;

import org.jgrades.lic.config.LicConfig;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public abstract class BaseTest {

    @Configuration
    @Import({TestAppPropertiesConfig.class, LicConfig.class})
    static class ContextConfiguration {

    }
}
