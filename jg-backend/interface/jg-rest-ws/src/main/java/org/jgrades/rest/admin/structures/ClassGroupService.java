/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.admin.structures;

import org.jgrades.admin.api.structures.ClassGroupMgntService;
import org.jgrades.data.api.entities.ClassGroup;
import org.jgrades.data.api.entities.roles.StudentDetails;
import org.jgrades.lic.api.aop.CheckLicence;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.aop.CheckSystemDependencies;
import org.jgrades.rest.api.admin.structures.IClassGroupService;
import org.jgrades.rest.common.AbstractRestCrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/classgroup", produces = MediaType.APPLICATION_JSON_VALUE)
@CheckSystemDependencies
@CheckLicence
public class ClassGroupService extends AbstractRestCrudPagingService<ClassGroup, Long, ClassGroupMgntService> implements IClassGroupService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(ClassGroupService.class);

    @Autowired
    protected ClassGroupService(ClassGroupMgntService crudService) {
        super(crudService);
    }

    @Override
    @RequestMapping(value = "/{id}/students", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','MANAGER')")
    public void setStudents(@PathVariable Long id, @RequestBody Set<StudentDetails> students) {
        getLogger().trace("Setting students to class group with id {}. Students are: {}", id, students);
        crudService.setStudents(crudService.getWithId(id), students);
    }

    @Override
    @RequestMapping(value = "/{id}/students", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','MANAGER','TEACHER')")
    public Set<StudentDetails> getStudents(@PathVariable Long id) {
        getLogger().trace("Getting students of class group with id {}", id);
        return crudService.getStudents(crudService.getWithId(id));
    }

    @Override
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','MANAGER','TEACHER')")
    public List<ClassGroup> getFromActiveSemester() {
        getLogger().trace("Getting class groups from active semester");
        return crudService.getFromActiveSemester();
    }

    @Override
    protected JgLogger getLogger() {
        return LOGGER; //NOSONAR
    }
}
