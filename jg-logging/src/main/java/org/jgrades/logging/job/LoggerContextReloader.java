package org.jgrades.logging.job;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.jgrades.logging.utils.InternalProperties;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LoggerContextReloader {
    public void reload() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);

        File externalXmlFile = new File(InternalProperties.XML_FILE);
        if(externalXmlFile.exists()){
            try {
                configurator.doConfigure(externalXmlFile);
            } catch (JoranException e) {
                setDefaultConfiguration(configurator);
            }
        } else{
            setDefaultConfiguration(configurator);
        }
    }

    private void setDefaultConfiguration(JoranConfigurator configurator){
        try {
            configurator.doConfigure(InternalProperties.DEFAULT_XML_FILE);
        } catch (JoranException e) {
            // not needed...
        }
    }
}
