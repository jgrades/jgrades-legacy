package org.jgrades.rest.logger;

import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.logging.model.LoggingConfiguration;
import org.jgrades.logging.model.LoggingStrategy;
import org.jgrades.logging.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

@RestController
@RequestMapping(value = "/logger")
public class LoggerConfigurationService {

    private static final JgLogger logger = JgLoggerFactory.getLogger(LoggerConfigurationService.class);

    @Autowired
    private LoggingService loggingService;

    @RequestMapping(value = "/checkConfiguration",  method = RequestMethod.GET)
    public boolean isUsingDefaultConfiguration(){
        logger.info("Checking is current configuration is default");

        return loggingService.isUsingDefaultConfiguration();
    }

    @RequestMapping(value = "/getConfiguration", method = RequestMethod.GET)
    public LoggingConfiguration getConfiguration(){
        logger.info("Get current logging configuration");

        return loggingService.getLoggingConfiguration();
    }

    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setNewConfiguration(@RequestBody LoggingConfiguration configuration) {
        logger.info("Set new default configuration");

        loggingService.setLoggingConfiguration(configuration);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
