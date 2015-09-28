package org.jgrades.rest.admin.structures;

import org.jgrades.admin.api.structures.SemesterMgntService;
import org.jgrades.data.api.entities.Semester;
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
@RequestMapping(value = "/semester", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class SemesterService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(SemesterService.class);

    @Autowired
    private SemesterMgntService semesterMgntService;

    @RequestMapping(value = "/migrate/{id}/{newSemesterName}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> migrate(@PathVariable Long id, @PathVariable String newSemesterName) {
        semesterMgntService.createNewByMigration(semesterMgntService.getWithId(id), newSemesterName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertOrUpdate(@RequestBody Semester semester) {
        semesterMgntService.saveOrUpdate(semester);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        semesterMgntService.remove(semesterMgntService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<Semester> getAll() {
        return semesterMgntService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Semester getWithId(@PathVariable Long id) {
        return semesterMgntService.getWithId(id);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public
    @ResponseBody
    Semester getActive() {
        return semesterMgntService.getActiveSemester();
    }

    @RequestMapping(value = "/active/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> setActive(@PathVariable Long id) {
        semesterMgntService.setActiveSemester(semesterMgntService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/page/{number}/{size}", method = RequestMethod.GET)
    public
    @ResponseBody
    Page<Semester> getPage(@PathVariable Integer number, @PathVariable Integer size) {
        PagingInfo pagingInfo = new PagingInfo(number, size);
        return semesterMgntService.getPage(pagingInfo.toPageable());
    }
}
