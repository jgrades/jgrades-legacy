/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.api.admin.structures;

import org.jgrades.data.api.entities.ClassGroup;
import org.jgrades.data.api.entities.roles.StudentDetails;
import org.jgrades.rest.api.common.RestCrudPagingService;

import java.util.List;
import java.util.Set;

public interface IClassGroupService extends RestCrudPagingService<ClassGroup, Long> {
    void setStudents(Long id, Set<StudentDetails> students);

    Set<StudentDetails> getStudents(Long id);

    List<ClassGroup> getFromActiveSemester();
}
