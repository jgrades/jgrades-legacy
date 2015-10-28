/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.common;

import org.jgrades.data.api.service.crud.CrudService;
import org.jgrades.lic.api.aop.CheckLicence;
import org.jgrades.logging.JgLogger;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.api.common.RestCrudService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CheckSystemDependencies
@CheckLicence
public abstract class AbstractRestCrudService<T, ID, S extends CrudService<T, ID>> implements RestCrudService<T, ID> { //NOSONAR
    protected final S crudService;

    protected AbstractRestCrudService(S crudService) {
        this.crudService = crudService;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','MANAGER')")
    public void insertOrUpdate(@RequestBody T entity) {
        getLogger().trace("Saving or updating entity: {}", entity);
        crudService.saveOrUpdate(entity);
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','MANAGER')")
    public void remove(@RequestParam("id") List<ID> ids) {
        getLogger().debug("Removing entities with ids: {} invoked", ids);
        crudService.removeIds(ids);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public T getWithId(@PathVariable ID id) {
        getLogger().trace("Getting entity with id {}", id);
        return crudService.getWithId(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public List<T> getWithIds(@RequestParam(value = "id", required = false) List<ID> ids) {
        getLogger().trace("Getting entities with ids: {}", ids);
        return ids == null ? crudService.getAll() : crudService.getWithIds(ids);
    }

    protected abstract JgLogger getLogger();
}
