package org.jgrades.rest.admin.structures;


import org.jgrades.admin.api.structures.YearLevelMgntService;
import org.jgrades.data.api.entities.YearLevel;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.PagingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/yearlevel", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class YearLevelService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(YearLevelService.class);

    @Autowired
    private YearLevelMgntService yearLevelMgntService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertOrUpdate(@RequestBody YearLevel yearLevel) {
        yearLevelMgntService.saveOrUpdate(yearLevel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        yearLevelMgntService.remove(yearLevelMgntService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<YearLevel> getAll() {
        return yearLevelMgntService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    YearLevel getWithId(@PathVariable Long id) {
        return yearLevelMgntService.getWithId(id);
    }

    @RequestMapping(value = "/page/{number}/{size}", method = RequestMethod.GET)
    public
    @ResponseBody
    Page<YearLevel> getPage(@PathVariable Integer number, @PathVariable Integer size) {
        PagingInfo pagingInfo = new PagingInfo(number, size);
        return yearLevelMgntService.getPage(pagingInfo.toPageable());
    }
}
