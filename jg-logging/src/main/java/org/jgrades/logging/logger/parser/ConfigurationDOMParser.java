package org.jgrades.logging.logger.parser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jgrades.logging.logger.configuration.ConfigurationUtils;
import org.jgrades.logging.logger.configuration.ConfigurationUtilsImpl;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;

    
public class ConfigurationDOMParser implements ConfigurationParser {

    private Document xmlFile;

    private Element elementLogFileSize;
    private Element elementLogStorageTimeLimit;

    private String logFileExtension;

    private ConfigurationUtils configurationUtils = new ConfigurationUtilsImpl();

    @Override
    public void parse(String pathToFile) throws ParserConfigurationException, IOException, SAXException {

        Validate.notEmpty(pathToFile);

        File configurationFile = new File(pathToFile);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder dombuilder = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cDocument = dombuilder.parse(configurationFile);

        DOMBuilder jdomBuilder = new DOMBuilder();
        xmlFile = jdomBuilder.build(w3cDocument);

    }

    @Override
    public void setLogFileStorageTimeLimit(int limit,String logbackConfigurationPath) throws IOException {
        checkElementsListSize();

        elementLogStorageTimeLimit.setText(String.valueOf(limit));

        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(xmlFile, new FileOutputStream(logbackConfigurationPath));
    }

    @Override
    public void setLogFileSize(String limit, String logbackConfigurationPath) throws IOException {
        int convertedLimit = convertToInteger(limit);
        checkElementsListSize();

        elementLogFileSize.setText(String.valueOf(convertedLimit) + logFileExtension);

        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(xmlFile, new FileOutputStream(logbackConfigurationPath));
    }

    @Override
    public int getElementLogFileSize(){
        checkElementsListSize();
        return Integer.parseInt(elementLogFileSize.getText().replaceAll(logFileExtension, StringUtils.EMPTY));
    }

    @Override
    public int getElementLogStorageTimeLimit(){
        checkElementsListSize();
        return Integer.parseInt(elementLogStorageTimeLimit.getText());
    }


    private int convertToInteger(String arg0) {
        int spaceNdx = arg0.indexOf(" ");
        logFileExtension =  arg0.substring(spaceNdx + 1);
        return Integer.parseInt(arg0.substring(0, spaceNdx));
    }

    private void findCurrentLogFileSize() {
        elementLogFileSize = configurationUtils.getCurrentLogFileSizeElement(xmlFile);
    }

    private void findCurrentLogFileStorageTimeLimit() {
        elementLogStorageTimeLimit = configurationUtils.getCurrentLogFileStorageTimeLimitElement(xmlFile);
    }

    private void checkElementsListSize(){
        if(elementLogFileSize == null ) { findCurrentLogFileSize(); }
        if(elementLogStorageTimeLimit == null ) { findCurrentLogFileStorageTimeLimit(); }

    }
}
