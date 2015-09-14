package org.jgrades.rest.admin.accounts;

import org.jgrades.admin.api.accounts.UserManagerService;
import org.jgrades.admin.api.accounts.UserSelectionService;
import org.jgrades.data.api.entities.User;
import org.jgrades.rest.PagingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractUserManagementService<U extends User> {
    @Autowired
    private UserManagerService<U> userManagerService;

    @Autowired
    private UserSelectionService<U> userSelectionService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertOrUpdate(@RequestBody U user) {
        userManagerService.saveOrUpdate(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        userManagerService.remove(userManagerService.getWithId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<U> getAll() {
        return userManagerService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    U getWithId(@PathVariable Long id) {
        return userManagerService.getWithId(id);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Page<U> getPage(@RequestBody PagingInfo pagingInfo) {
        return userManagerService.getPage(pagingInfo.toPageable());
    }
}
