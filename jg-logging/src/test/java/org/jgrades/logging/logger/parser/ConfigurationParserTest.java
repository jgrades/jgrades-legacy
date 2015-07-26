package org.jgrades.logging.logger.parser;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@Ignore
public class ConfigurationParserTest {

    private ConfigurationParser parser;
    private static final String LOG_BACK_CONFIGURATION_FILE_PATH = "src/test/resources/logback_test.xml";
    private static final String LOG_BACK_MODULE_CONFIGURATION_FILE_PATH = "src/test/resources/logback_per_module_logging_test.xml";
    private static final String LOG_BACK_TYPE_AND_MODULE_CONFIGURATION_PATH = "src/test/resources/logback_per_type_and_module_logging_test.xml";

    @Before
    public void init(){
        parser = new ConfigurationDOMParser();
    }

    @After
    public void clean() throws IOException {
        parser.setLogFileStorageTimeLimit(Integer.MAX_VALUE,LOG_BACK_CONFIGURATION_FILE_PATH,LOG_BACK_MODULE_CONFIGURATION_FILE_PATH);
        parser.setLogFileSize(String.valueOf(Integer.MAX_VALUE+" "+"MB"), LOG_BACK_CONFIGURATION_FILE_PATH,LOG_BACK_MODULE_CONFIGURATION_FILE_PATH);

        parser.copyContent(createOutputFileChannel(LOG_BACK_CONFIGURATION_FILE_PATH),createInputFileChannel(LOG_BACK_MODULE_CONFIGURATION_FILE_PATH));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyPathToLogBackConfiguration_ThrowException() throws ParserConfigurationException, IllegalAccessException, SAXException, IOException {

        parser.parse("");
    }

    @Test(expected = IOException.class)
    public void wrongPathToLogBackConfigurtation_ThrowException() throws ParserConfigurationException, IllegalAccessException, SAXException, IOException {

        parser.parse("fake/path/to/logback_test.xml");
    }

    @Test
    public void correctPathToLogBackConfiguration_NoException() throws ParserConfigurationException, IllegalAccessException, SAXException, IOException {

        parser.parse(LOG_BACK_CONFIGURATION_FILE_PATH);
    }

    @Test
    public void changeLogStorageTimeLimit__Module_Configuration_NoException() throws ParserConfigurationException, IllegalAccessException, SAXException, IOException {

        parser.parse(LOG_BACK_CONFIGURATION_FILE_PATH);
        parser.setLogFileStorageTimeLimit(365, LOG_BACK_CONFIGURATION_FILE_PATH, LOG_BACK_MODULE_CONFIGURATION_FILE_PATH);

        assertEquals(365, parser.getElementLogStorageTimeLimit());
    }

    @Test
    public void changeLogStorageTimeLimit_Module_Configuration_NoException() throws ParserConfigurationException, IllegalAccessException, SAXException, IOException {

        parser.parse(LOG_BACK_CONFIGURATION_FILE_PATH);
        parser.setLogFileSize("10 MB", LOG_BACK_CONFIGURATION_FILE_PATH, LOG_BACK_MODULE_CONFIGURATION_FILE_PATH);

        assertEquals(10, parser.getElementLogFileSize());
    }

    @Test
    public void copyContentFileTest_NoException() throws ParserConfigurationException, IllegalAccessException, SAXException, IOException {

        parser.parse(LOG_BACK_CONFIGURATION_FILE_PATH);
        parser.copyContent(createOutputFileChannel(LOG_BACK_CONFIGURATION_FILE_PATH), createInputFileChannel(LOG_BACK_TYPE_AND_MODULE_CONFIGURATION_PATH));

        assertEquals(FileUtils.readFileToString(new File(LOG_BACK_CONFIGURATION_FILE_PATH), "utf-8"),
                FileUtils.readFileToString(new File(LOG_BACK_TYPE_AND_MODULE_CONFIGURATION_PATH), "utf-8"));
    }

    private FileChannel createOutputFileChannel(String pathConf) throws IOException {
        RandomAccessFile file = new RandomAccessFile(pathConf,"rw");
        return file.getChannel();
    }

    private FileChannel createInputFileChannel(String pathConf) throws IOException {
        RandomAccessFile file = new RandomAccessFile(pathConf,"rw");
        return file.getChannel();
    }


}
