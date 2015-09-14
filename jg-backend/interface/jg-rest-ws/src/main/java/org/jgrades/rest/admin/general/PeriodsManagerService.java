package org.jgrades.rest.admin.general;

import org.jgrades.admin.api.general.PeriodsMgntService;
import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/period", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class PeriodsManagerService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(PeriodsManagerService.class);

    @Autowired
    private PeriodsMgntService periodsService;

    @Autowired
    private GeneralManagerService generalManagerService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertOrUpdate(@RequestBody SchoolDayPeriod schoolDayPeriod) {
        schoolDayPeriod.setSchool(generalManagerService.getGeneralData());
        periodsService.saveOrUpdate(schoolDayPeriod);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/generator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertWithGenerator(@RequestBody PeriodsGeneratorSettings generationSettings) {
        List<SchoolDayPeriod> periods = periodsService.generateManyWithGenerator(generationSettings);
        for (SchoolDayPeriod period : periods) {
            period.setSchool(generalManagerService.getGeneralData());
        }
        periodsService.saveMany(periods);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        periodsService.remove(periodsService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<SchoolDayPeriod> getAll() {
        return periodsService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    SchoolDayPeriod getWithId(@PathVariable Long id) {
        return periodsService.getWithId(id);
    }
}
