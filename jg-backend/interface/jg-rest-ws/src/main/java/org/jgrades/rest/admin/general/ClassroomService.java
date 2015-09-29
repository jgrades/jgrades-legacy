package org.jgrades.rest.admin.general;

import org.jgrades.admin.api.general.ClassroomMgntService;
import org.jgrades.data.api.entities.Classroom;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.admin.common.AbstractRestCrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/classroom", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class ClassroomService extends AbstractRestCrudPagingService<Classroom, Long, ClassroomMgntService> {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(ClassroomService.class);

    @Autowired
    protected ClassroomService(ClassroomMgntService crudService) {
        super(crudService);
    }
}
