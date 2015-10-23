/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.admin.accounts;

import io.swagger.annotations.ApiParam;
import org.jgrades.admin.api.accounts.MassAccountCreatorService;
import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.admin.api.model.MassAccountCreatorResultRecord;
import org.jgrades.admin.api.model.StudentCsvEntry;
import org.jgrades.data.api.entities.User;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.PagingInfo;
import org.jgrades.rest.admin.accounts.mass.StudentDataCsvParser;
import org.jgrades.rest.api.admin.accounts.IUserService;
import org.jgrades.rest.api.admin.accounts.MassCreatorDTO;
import org.jgrades.rest.common.AbstractRestCrudPagingService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class UserService extends AbstractRestCrudPagingService<User, Long, UserMgntService> implements IUserService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserSpecificationsBuilder userSpecificationsBuilder;

    @Autowired
    private MassAccountCreatorService massAccountCreatorService;

    @Autowired
    private StudentDataCsvParser csvParser;

    @Autowired
    protected UserService(UserMgntService crudService) {
        super(crudService);
    }

    @Override
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<User> getSearchResults(@RequestParam(value = "phrase", required = false) String phrase, //NOSONAR
                                       @RequestParam(value = "login", required = false) String login,
                                       @RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "surname", required = false) String surname,
                                       @RequestParam(value = "email", required = false) String email,
                                       @RequestParam(value = "roles", required = false) String roles,
                                       @RequestParam(value = "active", required = false) Boolean active,
                                       @RequestParam(value = "lastVisitFrom", required = false) DateTime lastVisitFrom,
                                       @RequestParam(value = "lastVisitTo", required = false) DateTime lastVisitTo) {
        Specification<User> userSpecification = userSpecificationsBuilder
                .withPhrase(phrase).withLogin(login).withName(name).withSurname(surname).withEmail(email)
                .withRoles(roles).withActiveState(active).withLastVisitBetween(lastVisitFrom, lastVisitTo)
                .build();
        return crudService.get(userSpecification);
    }

    @Override
    @RequestMapping(value = "/search/paging", method = RequestMethod.GET)
    public Page<User> getSearchResultsPage(@RequestParam(value = "page", defaultValue = "0") @ApiParam(value = "Page number") Integer number, //NOSONAR
                                           @RequestParam(value = "limit", defaultValue = "-1") @ApiParam(value = "Limit on page") Integer size,
                                           @RequestParam(value = "phrase", required = false) String phrase,
                                           @RequestParam(value = "login", required = false) String login,
                                           @RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "surname", required = false) String surname,
                                           @RequestParam(value = "email", required = false) String email,
                                           @RequestParam(value = "roles", required = false) String roles,
                                           @RequestParam(value = "active", required = false) Boolean active,
                                           @RequestParam(value = "lastVisitFrom", required = false) DateTime lastVisitFrom,
                                           @RequestParam(value = "lastVisitTo", required = false) DateTime lastVisitTo) {
        PagingInfo pagingInfo = new PagingInfo(number, size == -1 ? paginationLimit : size);
        Specification<User> userSpecification = userSpecificationsBuilder
                .withPhrase(phrase).withLogin(login).withName(name).withSurname(surname).withEmail(email)
                .withRoles(roles).withActiveState(active).withLastVisitBetween(lastVisitFrom, lastVisitTo)
                .build();
        return crudService.getPage(pagingInfo.toPageable(), userSpecification);
    }

    @Override
    @RequestMapping(value = "/mass", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<MassAccountCreatorResultRecord> massStudentsCreator(@RequestBody MassCreatorDTO massCreatorDTO) {
        getLogger().trace("Invoking mass student creator with settings {}", massCreatorDTO.getSettings());
        Set<StudentCsvEntry> studentsData = csvParser.parse(massCreatorDTO.getStudentCsvData());
        return massAccountCreatorService.createStudents(studentsData, massCreatorDTO.getSettings());
    }

    @Override
    protected JgLogger getLogger() {
        return LOGGER; //NOSONAR
    }

}
