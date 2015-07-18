package org.jgrades.logging.parser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Piotr on 2015-07-18.
 */
public interface ConfigurationParser {

    void parse(String pathToFile) throws ParserConfigurationException, IOException, SAXException;

    void setLogFileStorageTimeLimit(int limit,String newConfigurationLocalization) throws IOException;

    void setLogFileSize(int limit, String newConfigurationLocalization) throws IOException;

    int getElementLogFileSize();

    int getElementLogStorageTimeLimit();
}
