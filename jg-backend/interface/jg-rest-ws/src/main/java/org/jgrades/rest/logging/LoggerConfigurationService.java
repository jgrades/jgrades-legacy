package org.jgrades.rest.logging;

import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/logging")
public class LoggerConfigurationService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(LoggerConfigurationService.class);

    @Autowired
    private LoggingService loggingService;

    @RequestMapping(value = "/configuration/default", method = RequestMethod.GET)
    public boolean isUsingDefaultConfiguration() {
        LOGGER.info("Checking is current configuration is default");

        return loggingService.isUsingDefaultConfiguration();
    }

    @RequestMapping(value = "/configuration", method = RequestMethod.GET)
    public LoggingConfiguration getConfiguration() {
        LOGGER.info("Get current logging configuration");

        return loggingService.getLoggingConfiguration();
    }

    @RequestMapping(value = "/configuration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setNewConfiguration(@RequestBody LoggingConfiguration configuration) {
        LOGGER.info("Set new default configuration");

        loggingService.setLoggingConfiguration(configuration);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
