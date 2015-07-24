package org.jgrades.logging.logger.parser;

import com.google.common.io.Resources;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@Ignore
public class ConfigurationParserTest {

    private ConfigurationParser parser;
    private static final String LOG_BACK_CONFIGURATION_FILE_NAME = "logback_test.xml";
    private static final String LOG_BACK_CONFIGURATION_FILE_PATH = "src/test/resources/logback_test.xml";
    private String logback_conf_path;
    private int currentLogFileSize;
    private int currentLogFileStorageTimeLimit;


    @Before
    public void init(){
        parser = new ConfigurationDOMParser();
    }

    @After
    public void clean() throws IOException {
        //parser.setLogFileStorageTimeLimit(Integer.MAX_VALUE,LOG_BACK_CONFIGURATION_FILE_PATH);
        //parser.setLogFileSize(String.valueOf(Integer.MAX_VALUE),LOG_BACK_CONFIGURATION_FILE_PATH);
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
    public void changeLogStorageTimeLimit_NoException() throws ParserConfigurationException, IllegalAccessException, SAXException, IOException {

        parser.parse(LOG_BACK_CONFIGURATION_FILE_PATH);

        //parser.setLogFileStorageTimeLimit(365, LOG_BACK_CONFIGURATION_FILE_PATH);


    }
}
