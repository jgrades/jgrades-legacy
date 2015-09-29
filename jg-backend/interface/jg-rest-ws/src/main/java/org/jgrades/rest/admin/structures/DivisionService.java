package org.jgrades.rest.admin.structures;

import org.jgrades.admin.api.structures.DivisionMgntService;
import org.jgrades.data.api.entities.Division;
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
@RequestMapping(value = "/division", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class DivisionService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(DivisionService.class);

    @Autowired
    private DivisionMgntService divisionMgntService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertOrUpdate(@RequestBody Division division) {
        divisionMgntService.saveOrUpdate(division);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        divisionMgntService.remove(divisionMgntService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<Division> getAll() {
        return divisionMgntService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Division getWithId(@PathVariable Long id) {
        return divisionMgntService.getWithId(id);
    }

    @RequestMapping(value = "/page/{number}/{size}", method = RequestMethod.GET)
    public
    @ResponseBody
    Page<Division> getPage(@PathVariable Integer number, @PathVariable Integer size) {
        PagingInfo pagingInfo = new PagingInfo(number, size);
        return divisionMgntService.getPage(pagingInfo.toPageable());
    }
}
