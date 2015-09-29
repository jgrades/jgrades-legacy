package org.jgrades.rest.admin.general;

import org.jgrades.admin.api.general.SubjectsMgntService;
import org.jgrades.data.api.entities.Subject;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.admin.common.AbstractRestCrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/subject", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class SubjectService extends AbstractRestCrudPagingService<Subject, Long, SubjectsMgntService> {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(SubjectService.class);

    @Autowired
    protected SubjectService(SubjectsMgntService crudService) {
        super(crudService);
    }
}
