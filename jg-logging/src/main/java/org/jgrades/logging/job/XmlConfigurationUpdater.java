package org.jgrades.logging.job;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.model.LoggingStrategy;
import org.jgrades.logging.utils.InternalProperties;
import org.jgrades.logging.utils.LogbackXmlEditor;
import org.w3c.dom.NodeList;

public class XmlConfigurationUpdater {
    private LogbackXmlEditor xmlEditor = new LogbackXmlEditor();

    public void update(LoggingConfiguration targetConfig) {
        updateFileNames(targetConfig.getLoggingStrategy());
        updateLevel(targetConfig.getLevel());
        updateMaxFileSize(targetConfig.getMaxFileSize());
        updateMaxDays(targetConfig.getMaxDays());
        xmlEditor.saveWithChanges();
        LoggerContextReloader reloader = new LoggerContextReloader();
        reloader.reload();
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
            NodeList nodeList = xmlEditor.getNodes(".//root/@level");
            nodeList.item(0).setTextContent(level.toString().toUpperCase());
    }

    private void updateMaxFileSize(String maxFileSize) {
        NodeList nodeList = xmlEditor.getNodes(".//maxFileSize");
            for(int i=0; i<nodeList.getLength(); i++){
                nodeList.item(i).setTextContent(maxFileSize);
            }
    }

    private void updateMaxDays(Integer maxDays) {
        NodeList nodeList = xmlEditor.getNodes(".//maxHistory");
            for(int i=0; i<nodeList.getLength(); i++){
                nodeList.item(i).setTextContent(maxDays.toString());
            }
    }
}
