package org.jgrades.logging.parser;

import org.jdom2.filter.Filters;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;
/**
 * Created by Piotr on 2015-07-18.
 */
public class ConfigurationDOMParser implements ConfigurationParser {

    private Document xmlFile;

    private Element elementLogFileSize;
    private Element elementLogStorageTimeLimit;

    private static final String LOG_FILE_EXTENSION = "MB";

    @Override
    public void parse(String pathToFile) throws ParserConfigurationException, IOException, SAXException {

        File configurationFile = new File(pathToFile);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder dombuilder = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cDocument = dombuilder.parse(configurationFile);

        DOMBuilder jdomBuilder = new DOMBuilder();
        xmlFile = jdomBuilder.build(w3cDocument);

    }

    public void findCurrentLogFileStorageTimeLimit() {
        XPathExpression<Element> xpath = XPathFactory.instance().compile("/configuration/appender[2]/rollingPolicy/triggeringPolicy/MaxBackupIndex", Filters.element());
        elementLogStorageTimeLimit = xpath.evaluateFirst(xmlFile);
    }

    public void findCurrentLogFileSize() {
        XPathExpression<Element> xpath = XPathFactory.instance().compile("/configuration/appender[2]/rollingPolicy/triggeringPolicy/MaxFileSize[1]", Filters.element());
        elementLogFileSize = xpath.evaluateFirst(xmlFile);
    }

    @Override
    public void setLogFileStorageTimeLimit(int limit,String newConfigurationLocalization) throws IOException {
        if( elementLogStorageTimeLimit == null) {
            findCurrentLogFileStorageTimeLimit();
        }

        elementLogStorageTimeLimit.setText(String.valueOf(limit));
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(xmlFile, new FileOutputStream(newConfigurationLocalization));
    }

    @Override
    public void setLogFileSize(int limit, String newConfigurationLocalization) throws IOException {
        if( elementLogFileSize == null) {
            findCurrentLogFileSize();
        }

        elementLogFileSize.setText(String.valueOf(limit)+LOG_FILE_EXTENSION);
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(xmlFile, new FileOutputStream(newConfigurationLocalization));
    }

    @Override
    public int getElementLogFileSize(){
        return Integer.parseInt(elementLogFileSize.getText().replaceAll(LOG_FILE_EXTENSION ,""));
    }

    @Override
    public int getElementLogStorageTimeLimit(){
        return Integer.parseInt(elementLogStorageTimeLimit.getText());
    }
}
