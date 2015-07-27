package org.jgrades.logging.logger.parser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

public interface ConfigurationParser {

    void parse(String pathToFile) throws ParserConfigurationException, IOException, SAXException;

    void setLogFileStorageTimeLimit(int limit,String logbacConfigurationPath,String loggingTypeConfigurationPath) throws IOException;

    void setLogFileSize(String limit,String logbacConfigurationPath,String loggingTypeConfigurationPath) throws IOException;

    int getElementLogFileSize();

    int getElementLogStorageTimeLimit();

    void copyContent(FileChannel outputChannel,FileChannel inputChannel) throws IOException;

}
