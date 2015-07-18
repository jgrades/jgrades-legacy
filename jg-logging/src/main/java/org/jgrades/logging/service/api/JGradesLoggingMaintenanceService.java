package org.jgrades.logging.service.api;

import org.jgrades.logging.configuration.LoggingConfiguration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by Piotr on 2015-07-15.
 */
public interface JGradesLoggingMaintenanceService {

    void changeLogFileSize(int size) throws IOException, ParserConfigurationException, SAXException;

    void changeLogStoreTimeLimit(int limit);

    void changeLoggingConfiguration(LoggingConfiguration configuration);
}
