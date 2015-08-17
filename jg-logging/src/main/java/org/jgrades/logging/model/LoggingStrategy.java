package org.jgrades.logging.model;

import org.jdom2.Attribute;
import org.jgrades.logging.utils.LoggerInternalProperties;
import org.jgrades.logging.utils.XmlUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;

import static org.jgrades.logging.utils.LoggerInternalProperties.*;

public enum LoggingStrategy {
    LOG_FILE_PER_MODULE(new XmlFileNameTagsUpdater() {
        @Override
        public void updateStrategy() {
            try {
                NodeList nodeList = XmlUtils.getNodeList(".//fileNamePattern");
                for(int i=0; i<nodeList.getLength(); i++){
                    nodeList.item(i).setTextContent(LOGS_DIRECTORY + "/jg_${module-name-placeholder}_%d{yyyy-MM-dd}_%i.log");
                }
            } catch (XPathExpressionException e) {
                //not needed...
            }
        }
    }),

    LOG_FILE_PER_LEVEL(new XmlFileNameTagsUpdater() {
        @Override
        public void updateStrategy() {
            try {
                NodeList nodeList = XmlUtils.getNodeList(".//fileNamePattern");
                for(int i=0; i<nodeList.getLength(); i++){
                    String name = ((Element) nodeList.item(i).getParentNode().getParentNode()).getAttribute("name");
                    String levelName = name.substring(name.lastIndexOf("-") + 1);
                    nodeList.item(i).setTextContent(LOGS_DIRECTORY + "/jg_"+levelName+"_%d{yyyy-MM-dd}_%i.log");
                }
            } catch (XPathExpressionException e) {
                //not needed...
            }
        }
    }),

    LOG_FILE_PER_MODULE_AND_LEVEL(new XmlFileNameTagsUpdater() {
        @Override
        public void updateStrategy() {
            try {
                NodeList nodeList = XmlUtils.getNodeList(".//fileNamePattern");
                for(int i=0; i<nodeList.getLength(); i++){
                    String name = ((Element) nodeList.item(i).getParentNode().getParentNode()).getAttribute("name");
                    String levelName = name.substring(name.lastIndexOf("-") + 1);
                    nodeList.item(i).setTextContent(LOGS_DIRECTORY + "/jg_${module-name-placeholder}_"+levelName+"_%d{yyyy-MM-dd}_%i.log");
                }
            } catch (XPathExpressionException e) {
                //not needed...
            }
        }
    });

    private final XmlFileNameTagsUpdater updater;

    LoggingStrategy(XmlFileNameTagsUpdater updater) {
        this.updater = updater;
    }

    public XmlFileNameTagsUpdater getUpdater() {
        return updater;
    }
}
