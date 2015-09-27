package org.jgrades.rest.admin.general;

import org.jgrades.admin.api.general.DaysMgntService;
import org.jgrades.admin.api.model.WorkingDays;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.Set;

@RestController
@RequestMapping(value = "/workingdays", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class WorkingDaysService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(WorkingDaysService.class);

    @Autowired
    private DaysMgntService daysMgntService;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    Set<DayOfWeek> getWorkingDays() {
        return daysMgntService.getWorkingDays().getDays();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> setWorkingDays(@RequestBody Set<DayOfWeek> days) {
        WorkingDays workingDays = new WorkingDays();
        days.forEach(workingDays::addDay);
        daysMgntService.setWorkingDays(workingDays);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
