package org.jgrades.logging.logger.configuration.strategy;

import com.google.common.io.Resources;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;


public class ModuleConfiguration implements ConfigurationStrategy{

    private static final String LOG_BACK_CONFIGURATION_PER_MODULE = "src/main/resources/logback_per_module_logging.xml";

    @Override
    public FileChannel getFileChannel() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(getLogbackConfigurationFile(), "rw");
        return raf.getChannel();
    }

    @Override
    public List<Element> getListCurrentLogFileStorageTimeLimit(Document xmlFile) {
        List<Element> elementList = new ArrayList<>();
        XPathExpression<Element> xpath;

        xpath = XPathFactory.instance().compile("/configuration/appender/sift/appender/rollingPolicy/triggeringPolicy[2]/MaxBackupIndex",
                Filters.element());
        elementList.add(xpath.evaluateFirst(xmlFile));

        return elementList;
    }

    @Override
    public List<Element> getListCurrentLogFileSize(Document xmlFile) {
        List<Element> elementList = new ArrayList<>();
        XPathExpression<Element> xpath;

        xpath = XPathFactory.instance().compile("/configuration/appender/sift/appender/rollingPolicy/triggeringPolicy[1]/MaxFileSize",
                Filters.element());
        elementList.add(xpath.evaluateFirst(xmlFile));

        return elementList;
    }

    @Override
    public String getConfigurationFilePath() {
        return LOG_BACK_CONFIGURATION_PER_MODULE;
    }


    private String getLogbackConfigurationFile() throws IOException {
        URL url = Resources.getResource(getConfigurationFilePath());
        return url.getPath();
    }

}
