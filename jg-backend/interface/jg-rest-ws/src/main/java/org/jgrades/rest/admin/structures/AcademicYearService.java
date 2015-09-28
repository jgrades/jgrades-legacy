package org.jgrades.rest.admin.structures;

import org.jgrades.admin.api.structures.AcademicYearMgntService;
import org.jgrades.data.api.entities.AcademicYear;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.PagingInfo;
import org.jgrades.rest.admin.general.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/academicyear", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class AcademicYearService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(AcademicYearService.class);

    @Autowired
    private AcademicYearMgntService academicYearMgntService;

    @Autowired
    private SchoolService schoolService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertOrUpdate(@RequestBody AcademicYear academicYear) {
        academicYear.setSchool(schoolService.getGeneralData());
        academicYearMgntService.saveOrUpdate(academicYear);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        academicYearMgntService.remove(academicYearMgntService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<AcademicYear> getAll() {
        return academicYearMgntService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    AcademicYear getWithId(@PathVariable Long id) {
        return academicYearMgntService.getWithId(id);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public
    @ResponseBody
    AcademicYear getActive() {
        return academicYearMgntService.getActiveAcademicYear();
    }

    @RequestMapping(value = "/active/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> setActive(@PathVariable Long id) {
        academicYearMgntService.setActiveAcademicYear(academicYearMgntService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/page/{number}/{size}", method = RequestMethod.GET)
    public
    @ResponseBody
    Page<AcademicYear> getPage(@PathVariable Integer number, @PathVariable Integer size) {
        PagingInfo pagingInfo = new PagingInfo(number, size);
        return academicYearMgntService.getPage(pagingInfo.toPageable());
    }
}
