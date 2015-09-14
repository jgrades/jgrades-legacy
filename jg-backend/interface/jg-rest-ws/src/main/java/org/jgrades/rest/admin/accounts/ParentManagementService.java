package org.jgrades.rest.admin.accounts;

import org.jgrades.data.api.entities.Parent;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/parent", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class ParentManagementService extends AbstractUserManagementService<Parent> {
}
