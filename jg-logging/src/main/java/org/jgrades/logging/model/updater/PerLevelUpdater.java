package org.jgrades.logging.model.updater;

import org.jgrades.logging.model.XmlFileNameTagsUpdater;
import org.jgrades.logging.utils.LogbackXmlEditor;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import static org.jgrades.logging.utils.InternalProperties.LOGS_DIRECTORY;

public class PerLevelUpdater implements XmlFileNameTagsUpdater {
    private LogbackXmlEditor xmlEditor = new LogbackXmlEditor();

    @Override
    public void updateStrategy() {
        NodeList nodeList = xmlEditor.getNodes(".//fileNamePattern");
        for (int i = 0; i < nodeList.getLength(); i++) {
            String name = ((Element) nodeList.item(i).getParentNode().getParentNode()).getAttribute("name");
            String levelName = name.substring(name.lastIndexOf("-") + 1);
            nodeList.item(i).setTextContent(LOGS_DIRECTORY + "/jg_" + levelName + "_%d{yyyy-MM-dd}_%i.log");
        }
    }
}
