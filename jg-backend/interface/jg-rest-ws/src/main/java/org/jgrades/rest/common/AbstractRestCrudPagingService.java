/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.common;

import io.swagger.annotations.ApiParam;
import org.jgrades.data.api.service.crud.CrudPagingService;
import org.jgrades.lic.api.aop.CheckLicence;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.PagingInfo;
import org.jgrades.rest.api.common.RestCrudPagingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@CheckSystemDependencies
@CheckLicence
public abstract class AbstractRestCrudPagingService<T, ID, S extends CrudPagingService<T, ID>>  //NOSONAR
        extends AbstractRestCrudService<T, ID, S> implements RestCrudPagingService<T, ID> { //NOSONAR
    @Value("${rest.paging.default.limit}")
    protected Integer paginationLimit;

    protected AbstractRestCrudPagingService(S crudService) {
        super(crudService);
    }

    @Override
    @RequestMapping(value = "/paging", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public Page<T> getPage(@RequestParam(value = "page", defaultValue = "0") @ApiParam(value = "Page number") Integer number, @RequestParam(value = "limit", defaultValue = "-1") @ApiParam(value = "Limit on page") Integer size) {
        getLogger().trace("Getting page #{} of entities with limit {}", number, paginationLimit);
        PagingInfo pagingInfo = new PagingInfo(number, size == -1 ? paginationLimit : size);
        return crudService.getPage(pagingInfo.toPageable());
    }
}
