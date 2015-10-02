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
import org.jgrades.admin.common.AbstractPagingMgntService;
import org.jgrades.data.api.dao.structures.AcademicYearRepository;
import org.jgrades.data.api.entities.AcademicYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcademicYearMgntServiceImpl extends AbstractPagingMgntService<AcademicYear, Long, AcademicYearRepository> implements AcademicYearMgntService {

    @Autowired
    public AcademicYearMgntServiceImpl(AcademicYearRepository repository) {
        super(repository);
    }

    @Override
    public AcademicYear getActiveAcademicYear() {
        return repository.findOneByActiveTrue();
    }

    @Override
    @Transactional("mainTransactionManager")
    public void setActiveAcademicYear(AcademicYear academicYear) {
        AcademicYear actualActiveYear = repository.findOneByActiveTrue();
        if (actualActiveYear != null) {
            actualActiveYear.setActive(false);
            repository.save(actualActiveYear);
        }

        academicYear.setActive(true);
        repository.save(academicYear);
    }

    @Override
    public void saveOrUpdate(AcademicYear academicYear) {
        if (academicYear.isActive()) {
            setActiveAcademicYear(academicYear);
        } else {
            super.saveOrUpdate(academicYear);
        }
    }
}
