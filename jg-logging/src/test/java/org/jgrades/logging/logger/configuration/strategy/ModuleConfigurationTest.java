package org.jgrades.logging.logger.configuration.strategy;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.StrictAssertions.assertThat;


public class ModuleConfigurationTest {

    private ModuleConfiguration moduleConfiguration;
    private Document document;
    @Before
    public void init() throws ParserConfigurationException, IOException, SAXException {
        moduleConfiguration = new ModuleConfiguration();

        File configurationFile = new File("src/test/resources/logback_per_module_logging_test.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder dombuilder = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cDocument = dombuilder.parse(configurationFile);

        DOMBuilder jdomBuilder = new DOMBuilder();
        document = jdomBuilder.build(w3cDocument);
    }

    @Test
    public void getListCurrentLogFileStorageTimeLimitTest(){

        assertThat(moduleConfiguration.getListCurrentLogFileStorageTimeLimit(document)).asList().isNotEmpty();
        assertThat(moduleConfiguration.getListCurrentLogFileStorageTimeLimit(document)).asList().hasSize(1);
    }

    @Test
    public void  getListCurrentLogFileSizeTest(){

        assertThat(moduleConfiguration.getListCurrentLogFileSize(document)).asList().isNotEmpty();
        assertThat(moduleConfiguration.getListCurrentLogFileSize(document)).asList().hasSize(1);

    }
}
