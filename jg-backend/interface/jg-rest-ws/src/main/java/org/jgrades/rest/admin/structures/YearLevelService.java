package org.jgrades.rest.admin.structures;


import org.jgrades.admin.api.structures.YearLevelMgntService;
import org.jgrades.data.api.entities.YearLevel;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.admin.common.AbstractRestCrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/yearlevel", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class YearLevelService extends AbstractRestCrudPagingService<YearLevel, Long, YearLevelMgntService> {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(YearLevelService.class);

    @Autowired
    protected YearLevelService(YearLevelMgntService crudService) {
        super(crudService);
    }
}
