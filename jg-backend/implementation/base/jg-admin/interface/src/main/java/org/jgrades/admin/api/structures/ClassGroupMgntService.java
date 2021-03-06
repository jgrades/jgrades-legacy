/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.api.structures;

import org.jgrades.data.api.entities.ClassGroup;
import org.jgrades.data.api.entities.Division;
import org.jgrades.data.api.entities.roles.StudentDetails;
import org.jgrades.data.api.service.crud.CrudPagingService;

import java.util.List;
import java.util.Set;

public interface ClassGroupMgntService extends CrudPagingService<ClassGroup, Long> {
    List<ClassGroup> getFromActiveSemester();

    List<Division> getDivisions(ClassGroup classGroup);

    Set<StudentDetails> getStudents(ClassGroup classGroup);

    void setStudents(ClassGroup classGroup, Set<StudentDetails> students);
}
