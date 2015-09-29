package org.jgrades.rest.admin.structures;

import org.jgrades.admin.api.structures.SubGroupMgntService;
import org.jgrades.data.api.entities.SubGroup;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.admin.common.AbstractRestCrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/subgroup", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class SubGroupService extends AbstractRestCrudPagingService<SubGroup, Long, SubGroupMgntService> {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(SubGroupService.class);

    @Autowired
    protected SubGroupService(SubGroupMgntService crudService) {
        super(crudService);
    }
}
