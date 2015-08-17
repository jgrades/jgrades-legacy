package org.jgrades.logging.job;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.model.LoggingStrategy;
import org.jgrades.logging.utils.InternalProperties;
import org.jgrades.logging.utils.XmlUtils;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import java.io.File;

public class XmlConfigurationUpdater {
    public void update(LoggingConfiguration targetConfig) {
        updateFileNames(targetConfig.getLoggingStrategy());
        updateLevel(targetConfig.getLevel());
        updateMaxFileSize(targetConfig.getMaxFileSize());
        updateMaxDays(targetConfig.getMaxDays());
        XmlUtils.saveUpdated();
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

    private static void setDefaultConfiguration(JoranConfigurator configurator){
        try {
            configurator.doConfigure(InternalProperties.DEFAULT_XML_FILE);
        } catch (JoranException e) {
            // not needed...
        }
    }

    private void updateFileNames(LoggingStrategy loggingStrategy) {
        loggingStrategy.getUpdater().updateStrategy();
    }

    private void updateLevel(Level level) {
            NodeList nodeList = XmlUtils.getNodeList(".//root/@level");
            nodeList.item(0).setTextContent(level.toString().toUpperCase());
    }

    private void updateMaxFileSize(String maxFileSize) {
            NodeList nodeList = XmlUtils.getNodeList(".//maxFileSize");
            for(int i=0; i<nodeList.getLength(); i++){
                nodeList.item(i).setTextContent(maxFileSize);
            }
    }

    private void updateMaxDays(Integer maxDays) {
            NodeList nodeList = XmlUtils.getNodeList(".//maxHistory");
            for(int i=0; i<nodeList.getLength(); i++){
                nodeList.item(i).setTextContent(maxDays.toString());
            }
    }
}
