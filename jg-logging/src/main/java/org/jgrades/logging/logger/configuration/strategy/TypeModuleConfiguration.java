package org.jgrades.logging.logger.configuration.strategy;

import com.google.common.io.Resources;
import org.jdom2.Document;
import org.jdom2.Element;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.List;


public class TypeModuleConfiguration implements ConfigurationStrategy{

    private static final String LOG_BACK_CONFIGURATION_PER_TYPE_AND_MODULE = "logback_per_type_and_module_logging.xml";

    @Override
    public FileChannel getFileChannel() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(getLogbackConfigurationFile(), "rw");
        return raf.getChannel();
    }

    @Override
    public List<Element> getListCurrentLogFileStorageTimeLimit(Document xmlFile) {
        return null;
    }

    @Override
    public List<Element> getListCurrentLogFileSize(Document xmlFile) {
        return null;
    }

    @Override
    public String getConfigurationFilePath() {
        return LOG_BACK_CONFIGURATION_PER_TYPE_AND_MODULE;
    }

    private String getLogbackConfigurationFile() throws IOException {
        URL url = Resources.getResource(LOG_BACK_CONFIGURATION_PER_TYPE_AND_MODULE);
        return url.getPath();
    }

}
