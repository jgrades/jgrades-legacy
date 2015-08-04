package org.jgrades.logging.logger.configuration;

import com.google.common.io.Resources;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.jgrades.logging.logger.configuration.ConfigurationUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;

public class ConfigurationUtilsImpl implements ConfigurationUtils {

    private static final String LOGBACK_CONFIGURATION_FILE= "logback.xml";

    @Override
    public FileChannel getFileChannel() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(getLogbackConfigurationFile(), "rw");
        return raf.getChannel();
    }

    @Override
    public Element getCurrentLogFileStorageTimeLimitElement(Document xmlFile) {
        XPathExpression<Element> xpath;

        xpath = XPathFactory.instance().compile("/configuration/appender/sift/appender/rollingPolicy/triggeringPolicy[2]/MaxBackupIndex",
                Filters.element());

        return xpath.evaluateFirst(xmlFile);
    }

    @Override
    public Element getCurrentLogFileSizeElement(Document xmlFile) {
        XPathExpression<Element> xpath;

        xpath = XPathFactory.instance().compile("/configuration/appender/sift/appender/rollingPolicy/triggeringPolicy[1]/MaxFileSize",
                Filters.element());

        return xpath.evaluateFirst(xmlFile);
    }

    private String getLogbackConfigurationFile() throws IOException {
        URL url = Resources.getResource(LOGBACK_CONFIGURATION_FILE);
        return url.getPath();
    }

}
