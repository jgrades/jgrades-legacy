package org.jgrades.logging.model.updater;

import org.jgrades.logging.model.XmlFileNameTagsUpdater;
import org.jgrades.logging.utils.LogbackXmlEditor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.jgrades.logging.utils.InternalProperties.LOGS_DIRECTORY;

public class PerModuleAndLevelUpdater implements XmlFileNameTagsUpdater {
    private LogbackXmlEditor xmlEditor = new LogbackXmlEditor();

    @Override
    public void updateFileNameTags() {
        NodeList nodeList = xmlEditor.getFileNamePatternNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            String levelName = xmlEditor.getLevelNameFromAppenderName(node);
            String newFileNamePattern = node.getTextContent().contains("external-lib") ?
                    LOGS_DIRECTORY + "/jg_external-lib_" + levelName + "_%d{yyyy-MM-dd}_%i.log" :
                    LOGS_DIRECTORY + "/jg_${module-name-placeholder}_" + levelName + "_%d{yyyy-MM-dd}_%i.log";
            node.setTextContent(newFileNamePattern);
        }
    }
}
