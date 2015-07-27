package org.jgrades.logging.logger.service.api;

import org.jgrades.logging.logger.configuration.LoggingConfiguration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import ch.qos.logback.classic.Level;


public interface JgLoggingService {

    void setLevel(Level level);

    void setLoggingMode(LoggingConfiguration mode);

    void setMaxSize(String size);

    void setCleaningAfterDays(Integer days);

    LoggingConfiguration getLoggingConfiguration();


}
