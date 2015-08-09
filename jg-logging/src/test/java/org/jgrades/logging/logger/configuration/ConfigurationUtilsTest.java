package org.jgrades.logging.logger.configuration;

import org.jdom2.Element;
import org.jgrades.logging.logger.parser.ConfigurationDOMParser;
import org.jgrades.logging.logger.parser.ConfigurationParser;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationUtilsTest {

    private ConfigurationUtils utils;
    private ConfigurationDOMParser configurationParser;
    private final String pathToConf = "src/test/resources/logback.xml";

    private final String currentTimeLimit = "2147483647";
    private final String currentLogSize = "2147483647";
    @Before
    public void init(){
        utils = new ConfigurationUtilsImpl();
        configurationParser = new ConfigurationDOMParser();
    }
    @Test
    public void getFileChannelTest_NoExcetpion() throws IOException {

        assertThat(utils.getFileChannel()).isNotNull();
    }

    @Test
    public void getCurrentLogFileStorageTimeLimitElement_NoException() throws IOException, SAXException, ParserConfigurationException {
        configurationParser.parse(pathToConf);

        assertThat(utils.getCurrentLogFileStorageTimeLimitElement(configurationParser.getXmlFile())).matches(new Predicate<Element>() {
            @Override
            public boolean test(Element element) {
                return element.getText().equals(currentTimeLimit);
            }
        });
    }

    @Test
    public void getCurrentLogFileSizeElementTest_NoException() throws IOException, SAXException, ParserConfigurationException {
        configurationParser.parse(pathToConf);

        assertThat(utils.getCurrentLogFileStorageTimeLimitElement(configurationParser.getXmlFile())).matches(new Predicate<Element>() {
            @Override
            public boolean test(Element element) {
                return element.getText().equals(currentLogSize);
            }
        });
    }
}
