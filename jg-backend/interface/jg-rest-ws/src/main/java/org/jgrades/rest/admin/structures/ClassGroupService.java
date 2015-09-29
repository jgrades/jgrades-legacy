package org.jgrades.rest.admin.structures;

import org.jgrades.admin.api.structures.ClassGroupMgntService;
import org.jgrades.data.api.entities.ClassGroup;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.admin.common.AbstractRestCrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/classgroup", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class ClassGroupService extends AbstractRestCrudPagingService<ClassGroup, Long, ClassGroupMgntService> {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(ClassGroupService.class);

    @Autowired
    protected ClassGroupService(ClassGroupMgntService crudService) {
        super(crudService);
    }
}
