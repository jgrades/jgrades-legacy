package org.jgrades.logging.logger.parser;

import org.apache.commons.lang3.Validate;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jgrades.logging.logger.configuration.strategy.ConfigurationStrategy;
import org.jgrades.logging.logger.utils.PropertyUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;

    
public class ConfigurationDOMParser implements ConfigurationParser {

    private Document xmlFile;

    private List<Element> elementLogFileSize = new ArrayList<>();
    private List<Element> elementLogStorageTimeLimit = new ArrayList<>();

    private static final String LOG_FILE_EXTENSION = "MB";

    private final static long KB_FACTOR = 1024;
    private final static long MB_FACTOR = 1024 * KB_FACTOR;
    private final static long GB_FACTOR = 1024 * MB_FACTOR;

    private final static String LOG_BACK_PATH_TO_CONFIGURATION = "src/main/resources/logback.xml";

    private ConfigurationStrategy configurationStrategy;

    @Override
    public void parse(String pathToFile) throws ParserConfigurationException, IOException, SAXException, IllegalAccessException {

        Validate.notEmpty(pathToFile);

        File configurationFile = new File(pathToFile);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder dombuilder = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cDocument = dombuilder.parse(configurationFile);

        DOMBuilder jdomBuilder = new DOMBuilder();
        xmlFile = jdomBuilder.build(w3cDocument);

        configurationStrategy = PropertyUtils.readConfigurationStrategy(PropertyUtils.getCurrentLoggerConfiguration());

    }

    private void findCurrentLogFileSize() {
        elementLogFileSize = configurationStrategy.getListCurrentLogFileSize(xmlFile);
    }

    private void findCurrentLogFileStorageTimeLimit() {
        elementLogStorageTimeLimit = configurationStrategy.getListCurrentLogFileStorageTimeLimit(xmlFile);
    }

    @Override
    public void setLogFileStorageTimeLimit(int limit,String logbackConfigurationPath,String loggingTypeConfigurationPath) throws IOException {
        if( elementLogStorageTimeLimit.size() == 0) {
            findCurrentLogFileStorageTimeLimit();
        }

        elementLogStorageTimeLimit.forEach(new Consumer<Element>() {
            @Override
            public void accept(Element element) {
                element.setText(String.valueOf(limit));
            }
        });

        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(xmlFile, new FileOutputStream(logbackConfigurationPath));
        xmlOutputter.output(xmlFile, new FileOutputStream(loggingTypeConfigurationPath));
    }

    @Override
    public void setLogFileSize(String limit, String logbackConfigurationPath,String loggingTypeConfigurationPath) throws IOException {

        int convertedLimit = convertToInteger(limit);
        if( elementLogFileSize.size() == 0) {
            findCurrentLogFileSize();
        }

        elementLogFileSize.forEach(new Consumer<Element>() {
            @Override
            public void accept(Element element) {
                element.setText(String.valueOf(convertedLimit) + LOG_FILE_EXTENSION);
            }
        });

        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(xmlFile, new FileOutputStream(logbackConfigurationPath));
        xmlOutputter.output(xmlFile, new FileOutputStream(loggingTypeConfigurationPath));
    }

    @Override
    public int getElementLogFileSize(){
        return Integer.parseInt(elementLogFileSize.get(0).getText().replaceAll(LOG_FILE_EXTENSION ,""));
    }

    @Override
    public int getElementLogStorageTimeLimit(){
        return Integer.parseInt(elementLogStorageTimeLimit.get(0).getText());
    }

    @Override
    public void copyContent(FileChannel input) throws IOException {
        FileChannel output = createOutputFileChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(6 * 1024);
        long len = 0;
        while((len = input.read(buffer)) != -1) {
            buffer.flip();
            output.write(buffer);
            buffer.clear();
        }

        output.close();
        input.close();

    }

    @Override
    public String getLogBackPathToConfiguration() {
        return LOG_BACK_PATH_TO_CONFIGURATION;
    }

    private FileChannel createOutputFileChannel() throws FileNotFoundException {
        FileOutputStream outputStream = new FileOutputStream(new File(getLogBackPathToConfiguration()));
        return outputStream.getChannel();
    }

    private int convertToInteger(String arg0) {
        int spaceNdx = arg0.indexOf(" ");
        int value = Integer.parseInt(arg0.substring(0, spaceNdx));
        String factor = arg0.substring(spaceNdx + 1);
        switch (factor) {
            case "GB":
                return (int) (value * GB_FACTOR);
            case "MB":
                return (int) (value * MB_FACTOR);
            case "KB":
                return (int) (value * KB_FACTOR);
        }
        return -1;
    }
}
