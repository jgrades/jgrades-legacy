package org.jgrades.rest.admin;

import org.jgrades.admin.api.general.SubjectsMgntService;
import org.jgrades.data.api.entities.Subject;
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
@RequestMapping(value = "/subject", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class SubjectManagerService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(SubjectManagerService.class);

    @Autowired
    private SubjectsMgntService subjectService;


    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertOrUpdate(@RequestBody Subject subject) {
        subjectService.saveOrUpdate(subject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        subjectService.remove(subjectService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<Subject> getAll() {
        return subjectService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Subject getWithId(@PathVariable Long id) {
        return subjectService.getWithId(id);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Page<Subject> getPage(@RequestBody PagingInfo pagingInfo) {
        return subjectService.getPage(pagingInfo.toPageable());
    }
}
