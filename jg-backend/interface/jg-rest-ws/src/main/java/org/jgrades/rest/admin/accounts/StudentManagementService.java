package org.jgrades.rest.admin.accounts;

import org.jgrades.data.api.entities.Student;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/student", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class StudentManagementService extends AbstractUserManagementService<Student> {
}
