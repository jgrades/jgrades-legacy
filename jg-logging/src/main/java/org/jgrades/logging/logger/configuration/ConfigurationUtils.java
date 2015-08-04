package org.jgrades.logging.logger.configuration;

import org.jdom2.Document;
import org.jdom2.Element;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;


public interface ConfigurationUtils {

    FileChannel getFileChannel() throws IOException;

    Element getCurrentLogFileStorageTimeLimitElement(Document xmlFile);

    Element getCurrentLogFileSizeElement(Document xmlFile);

}
