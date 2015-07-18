package org.jgrades.logging.parser;

import com.google.common.io.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by Piotr on 2015-07-18.
 */
public class ConfigurationParserTest {

    private ConfigurationParser parser;
    private static final String LOG_BACK_CONFIGURATION_FILE_NAME = "logback_test.xml";
    private static final String LOG_BACK_CONFIGURATION_FILE_PATH = "src/test/resources/logback_test.xml";
    private String logback_conf_path;
    private int currentLogFileSize;
    private int currentLogFileStorageTimeLimit;

    @Before
    public void init() throws IOException {
        parser = getDOMImplementation();
        logback_conf_path = getLogbackConfigurationFile();
    }

    @After
    public void clean() throws IOException {
        parser.setLogFileStorageTimeLimit(Integer.MAX_VALUE,LOG_BACK_CONFIGURATION_FILE_PATH);
        parser.setLogFileSize(Integer.MAX_VALUE, LOG_BACK_CONFIGURATION_FILE_PATH);
    }
    @Test
    public void parseTest_NoExceptionShouldThrow() throws IOException, SAXException, ParserConfigurationException {

        parser.parse(logback_conf_path);
    }

    @Test
    public void setNewValueOfLogSizeValueTest_NoExceptionShouldBeThrow() throws IOException, SAXException, ParserConfigurationException {

        parser.parse(logback_conf_path);

        parser.setLogFileSize(10, "src/test/resources/logback_test.xml");

        assertEquals(10, parser.getElementLogFileSize());

    }

    @Test
    public void setNewValueOfLogStorageTimeLimitValueTest_NoExceptionShouldBeThrow() throws IOException, SAXException, ParserConfigurationException {

        parser.parse(logback_conf_path);

        parser.setLogFileStorageTimeLimit(10, "src/test/resources/logback_test.xml");

        assertEquals(10,parser.getElementLogStorageTimeLimit());


    }

    private ConfigurationParser getDOMImplementation() {
        return new ConfigurationDOMParser();
    }

    private String getLogbackConfigurationFile() throws IOException {
        URL url = Resources.getResource(LOG_BACK_CONFIGURATION_FILE_NAME);
        return url.getPath();
    }


}
