package org.jgrades.rest.data;

import org.jgrades.data.api.model.DataSourceDetails;
import org.jgrades.data.api.service.DataSourceService;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.monitor.api.model.SystemDependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/datasource", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies(ignored = SystemDependency.MAIN_DATA_SOURCE)
public class DataSourceManagementService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(DataSourceManagementService.class);

    @Autowired
    private DataSourceService dataSourceService;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    DataSourceDetails getDataSourceDetails() {
        return dataSourceService.getDataSourceDetails();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DataSourceDetails> setDataSourceDetails(@RequestBody DataSourceDetails details) {
        dataSourceService.setDataSourceDetails(details);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}
