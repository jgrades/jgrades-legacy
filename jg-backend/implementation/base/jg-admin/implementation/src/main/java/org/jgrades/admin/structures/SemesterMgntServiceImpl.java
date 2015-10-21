/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.structures;

import org.jgrades.admin.api.structures.AcademicYearMgntService;
import org.jgrades.admin.api.structures.SemesterMgntService;
import org.jgrades.data.api.dao.structures.SemesterRepository;
import org.jgrades.data.api.entities.Semester;
import org.jgrades.data.api.service.crud.AbstractPagingMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SemesterMgntServiceImpl extends AbstractPagingMgntService<Semester, Long, SemesterRepository> implements SemesterMgntService {
    @Autowired
    private AcademicYearMgntService academicYearMgntService;

    @Autowired
    public SemesterMgntServiceImpl(SemesterRepository repository) {
        super(repository);
    }

    @Override//TODO
    @Transactional("mainTransactionManager")
    public Semester createNewByMigration(Semester previousSemester, String newSemesterName) {
        return null;
    }

    @Override
    public Semester getActiveSemester() {
        return repository.findOneByActiveTrue();
    }

    @Override
    @Transactional("mainTransactionManager")
    public void setActiveSemester(Semester semester) {
        semester.setAcademicYear(academicYearMgntService.getActiveAcademicYear());
        Semester actualActiveSemester = getActiveSemester();
        if (actualActiveSemester != null) {
            actualActiveSemester.setActive(false);
            repository.save(actualActiveSemester);
        }

        semester.setActive(true);
        repository.save(semester);
    }

    @Override
    public void saveOrUpdate(Semester semester) {
        semester.setAcademicYear(academicYearMgntService.getActiveAcademicYear());
        if (semester.isActive()) {
            setActiveSemester(semester);
        } else {
            super.saveOrUpdate(semester);
        }
    }
}
