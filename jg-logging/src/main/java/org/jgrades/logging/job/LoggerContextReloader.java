package org.jgrades.logging.job;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.jgrades.logging.utils.InternalProperties;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LoggerContextReloader {
    private JoranConfigurator configurator;

    public void reload() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.reset();
        configurator = new JoranConfigurator();
        configurator.setContext(context);

        File externalXmlFile = new File(InternalProperties.XML_FILE);
        if(externalXmlFile.exists()){
            setConfiguration(externalXmlFile.getAbsolutePath());
        } else{
            setDefaultConfiguration();
        }
    }

    private void setConfiguration(String configXmlFilePath){
        try {
            configurator.doConfigure(configXmlFilePath);
        } catch (JoranException e) {
            setDefaultConfiguration();
        }
    }

    private void setDefaultConfiguration(){
        setConfiguration(InternalProperties.ONLY_CONSOLE_XML_FILE);
    }
}
