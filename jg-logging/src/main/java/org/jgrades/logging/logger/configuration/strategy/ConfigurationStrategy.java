package org.jgrades.logging.logger.configuration.strategy;

import org.jdom2.Document;
import org.jdom2.Element;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by Piotr on 2015-07-23.
 */
public interface ConfigurationStrategy {

    FileChannel getFileChannel() throws IOException;

    List<Element> getListCurrentLogFileStorageTimeLimit(Document xmlFile);

    List<Element>getListCurrentLogFileSize(Document xmlFile);

    String getConfigurationFilePath();
}
