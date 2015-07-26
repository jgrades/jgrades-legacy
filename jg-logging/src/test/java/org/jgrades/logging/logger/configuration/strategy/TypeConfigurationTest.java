package org.jgrades.logging.logger.configuration.strategy;

import org.jdom2.Document;
import org.jdom2.input.DOMBuilder;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.StrictAssertions.assertThat;

/**
 * Created by Piotr on 2015-07-26.
 */
public class TypeConfigurationTest {

    private TypeConfiguration typeConfiguration;
    private Document document;
    @Before
    public void init() throws ParserConfigurationException, IOException, SAXException {
        typeConfiguration = new TypeConfiguration();

        File configurationFile = new File("src/test/resources/logback_per_type_logging_test.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder dombuilder = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cDocument = dombuilder.parse(configurationFile);

        DOMBuilder jdomBuilder = new DOMBuilder();
        document = jdomBuilder.build(w3cDocument);
    }

    @Test
    public void getListCurrentLogFileStorageTimeLimitTest(){

        assertThat(typeConfiguration.getListCurrentLogFileStorageTimeLimit(document)).asList().isNotEmpty();
        assertThat(typeConfiguration.getListCurrentLogFileStorageTimeLimit(document)).asList().hasSize(3);
    }

    @Test
    public void  getListCurrentLogFileSizeTest(){

        assertThat(typeConfiguration.getListCurrentLogFileSize(document)).asList().isNotEmpty();
        assertThat(typeConfiguration.getListCurrentLogFileSize(document)).asList().hasSize(3);

    }
}
