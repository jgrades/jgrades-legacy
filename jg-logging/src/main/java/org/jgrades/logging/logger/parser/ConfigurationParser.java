package org.jgrades.logging.logger.parser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

public interface ConfigurationParser {

    void parse(String pathToFile) throws ParserConfigurationException, IOException, SAXException;

    void setLogFileStorageTimeLimit(int limit,String logbackConfiguration) throws IOException;

    void setLogFileSize(String limit,String logbackConfiguration) throws IOException;

    int getElementLogFileSize();

    int getElementLogStorageTimeLimit();


}
