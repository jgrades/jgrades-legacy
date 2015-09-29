package org.jgrades.rest.admin.structures;

import org.jgrades.admin.api.structures.ClassGroupMgntService;
import org.jgrades.data.api.entities.ClassGroup;
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
@RequestMapping(value = "/classgroup", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class ClassGroupService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(ClassGroupService.class);

    @Autowired
    private ClassGroupMgntService classGroupMgntService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertOrUpdate(@RequestBody ClassGroup classGroup) {
        classGroupMgntService.saveOrUpdate(classGroup);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        classGroupMgntService.remove(classGroupMgntService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<ClassGroup> getAll() {
        return classGroupMgntService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ClassGroup getWithId(@PathVariable Long id) {
        return classGroupMgntService.getWithId(id);
    }

    @RequestMapping(value = "/page/{number}/{size}", method = RequestMethod.GET)
    public
    @ResponseBody
    Page<ClassGroup> getPage(@PathVariable Integer number, @PathVariable Integer size) {
        PagingInfo pagingInfo = new PagingInfo(number, size);
        return classGroupMgntService.getPage(pagingInfo.toPageable());
    }
}
