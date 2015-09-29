package org.jgrades.rest.admin.structures;

import org.jgrades.admin.api.structures.SubGroupMgntService;
import org.jgrades.data.api.entities.SubGroup;
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
@RequestMapping(value = "/subgroup", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class SubGroupService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(SubGroupService.class);

    @Autowired
    private SubGroupMgntService subGroupMgntService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertOrUpdate(@RequestBody SubGroup subGroup) {
        subGroupMgntService.saveOrUpdate(subGroup);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        subGroupMgntService.remove(subGroupMgntService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<SubGroup> getAll() {
        return subGroupMgntService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    SubGroup getWithId(@PathVariable Long id) {
        return subGroupMgntService.getWithId(id);
    }

    @RequestMapping(value = "/page/{number}/{size}", method = RequestMethod.GET)
    public
    @ResponseBody
    Page<SubGroup> getPage(@PathVariable Integer number, @PathVariable Integer size) {
        PagingInfo pagingInfo = new PagingInfo(number, size);
        return subGroupMgntService.getPage(pagingInfo.toPageable());
    }
}
