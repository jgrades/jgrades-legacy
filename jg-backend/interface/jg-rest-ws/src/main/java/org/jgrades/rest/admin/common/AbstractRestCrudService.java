package org.jgrades.rest.admin.common;

import org.jgrades.admin.api.common.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractRestCrudService<T, ID, S extends CrudService<T, ID>> {
    protected final S crudService;

    protected AbstractRestCrudService(S crudService) {
        this.crudService = crudService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertOrUpdate(@RequestBody T entity) {
        crudService.saveOrUpdate(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable ID id) {
        crudService.remove(crudService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<T> getAll() {
        return crudService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    T getWithId(@PathVariable ID id) {
        return crudService.getWithId(id);
    }
}
