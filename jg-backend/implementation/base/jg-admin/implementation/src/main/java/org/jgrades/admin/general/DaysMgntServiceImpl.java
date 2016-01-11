/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.general;

import com.google.common.collect.Sets;
import org.jgrades.admin.api.general.DaysMgntService;
import org.jgrades.admin.api.general.GeneralDataService;
import org.jgrades.admin.api.model.WorkingDays;
import org.jgrades.data.api.dao.SchoolDayRepository;
import org.jgrades.data.api.entities.SchoolDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class DaysMgntServiceImpl implements DaysMgntService {
    @Autowired
    private SchoolDayRepository repository;

    @Autowired
    private GeneralDataService generalDataService;

    @Override
    public WorkingDays getWorkingDays() {
        WorkingDays result = new WorkingDays();
        Iterable<SchoolDay> schoolDays = repository.findAll();
        for (SchoolDay schoolDay : schoolDays) {
            result.addDay(schoolDay.getDayOfWeek());
        }
        return result;
    }

    @Override
    public void setWorkingDays(WorkingDays newWorkingDays) {
        WorkingDays actualWorkingDays = getWorkingDays();

        for (DayOfWeek newDay : Sets.difference(newWorkingDays.getDays(), actualWorkingDays.getDays())) {
            SchoolDay schoolDay = new SchoolDay(newDay.getValue(), newDay);
            schoolDay.setSchool(generalDataService.getSchoolGeneralDetails());
            repository.save(schoolDay);
        }

        for (DayOfWeek toRemoveDay : Sets.difference(actualWorkingDays.getDays(), newWorkingDays.getDays())) {
            repository.delete(toRemoveDay.getValue());
        }
    }
}
