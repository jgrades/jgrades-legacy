package org.jgrades.logging.model.updater;

import org.jgrades.logging.model.XmlFileNameTagsUpdater;
import org.jgrades.logging.utils.LogbackXmlEditor;
import org.w3c.dom.NodeList;

import static org.jgrades.logging.utils.InternalProperties.LOGS_DIRECTORY;

public class PerModuleUpdater implements XmlFileNameTagsUpdater {
    private LogbackXmlEditor xmlEditor = new LogbackXmlEditor();

    @Override
    public void updateStrategy() {
        NodeList nodeList = xmlEditor.getNodes(".//fileNamePattern");
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getTextContent().contains("external-lib")) {
                nodeList.item(i).setTextContent(LOGS_DIRECTORY + "/jg_external-lib_%d{yyyy-MM-dd}_%i.log");
            } else {
                nodeList.item(i).setTextContent(LOGS_DIRECTORY + "/jg_${module-name-placeholder}_%d{yyyy-MM-dd}_%i.log");
            }
        }
    }
}
