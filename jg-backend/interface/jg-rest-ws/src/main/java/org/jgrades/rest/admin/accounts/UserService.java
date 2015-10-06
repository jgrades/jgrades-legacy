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
import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.data.api.entities.User;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.PagingInfo;
import org.jgrades.rest.admin.common.AbstractRestCrudPagingService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
public class UserService extends AbstractRestCrudPagingService<User, Long, UserMgntService> {
    @Autowired
    private UserSpecificationsBuilder userSpecificationsBuilder;

    @Autowired
    protected UserService(UserMgntService crudService) {
        super(crudService);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public
    @ResponseBody
    List<User> getSearchResults(@RequestParam(value = "phrase", required = false) String phrase,
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

    @RequestMapping(value = "/search/paging", method = RequestMethod.GET)
    public
    @ResponseBody
    Page<User> getSearchResultsPage(@RequestParam(value = "page", defaultValue = "0") @ApiParam(value = "Page number") Integer number,
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
}