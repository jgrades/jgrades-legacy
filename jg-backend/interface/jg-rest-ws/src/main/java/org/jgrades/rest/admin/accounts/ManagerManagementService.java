package org.jgrades.rest.admin.accounts;

import org.jgrades.data.api.entities.Manager;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/manager", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class ManagerManagementService extends AbstractUserManagementService<Manager> {
}
