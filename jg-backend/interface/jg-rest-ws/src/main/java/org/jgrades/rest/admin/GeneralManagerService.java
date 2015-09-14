package org.jgrades.rest.admin;

import org.jgrades.admin.api.general.GeneralDataService;
import org.jgrades.data.api.entities.School;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/general", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class GeneralManagerService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(GeneralManagerService.class);

    @Autowired
    private GeneralDataService generalDataService;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    School getGeneralData() {
        return generalDataService.getSchoolGeneralDetails();
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertOrUpdate(@RequestBody School generalData) {
        generalDataService.setSchoolGeneralDetails(generalData);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
