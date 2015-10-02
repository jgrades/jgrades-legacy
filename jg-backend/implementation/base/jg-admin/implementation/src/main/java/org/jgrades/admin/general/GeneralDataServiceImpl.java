/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.general;

import org.jgrades.admin.api.general.GeneralDataService;
import org.jgrades.data.api.dao.SchoolRepository;
import org.jgrades.data.api.entities.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class GeneralDataServiceImpl implements GeneralDataService {
    @Autowired
    private SchoolRepository repository;

    @Override
    public School getSchoolGeneralDetails() {
        Iterator<School> schoolIterator = repository.findAll().iterator();
        return schoolIterator.hasNext() ? schoolIterator.next() : null;
    }

    @Override
    public void setSchoolGeneralDetails(School school) {
        if (repository.count() == 1) {
            School actualSchool = getSchoolGeneralDetails();
            school.setId(actualSchool.getId());
        }
        repository.save(school);
    }
}
