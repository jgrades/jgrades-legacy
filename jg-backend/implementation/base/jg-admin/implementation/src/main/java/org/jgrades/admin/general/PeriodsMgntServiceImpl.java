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

import com.google.common.collect.Lists;
import org.jgrades.admin.api.general.PeriodsMgntService;
import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.data.api.dao.SchoolDayPeriodRepository;
import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.jgrades.data.api.service.crud.AbstractMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
public class PeriodsMgntServiceImpl extends AbstractMgntService<SchoolDayPeriod, Long, SchoolDayPeriodRepository> implements PeriodsMgntService {
    @Autowired
    public PeriodsMgntServiceImpl(SchoolDayPeriodRepository repository) {
        super(repository);
    }

    @Override
    @Transactional("mainTransactionManager")
    public List<SchoolDayPeriod> generateManyWithGenerator(PeriodsGeneratorSettings settings) {
        List<SchoolDayPeriod> generatedPeriods = Lists.newArrayList();

        LocalTime firstLessonStartTime = settings.getFirstLessonTime();

        SchoolDayPeriod firstPeriod = new SchoolDayPeriod();
        firstPeriod.setStartTime(firstLessonStartTime);
        firstPeriod.setEndTime(firstLessonStartTime.plusMinutes(settings.getLessonDurationMinutes()));
        generatedPeriods.add(firstPeriod);

        LocalTime endTime = firstPeriod.getEndTime();
        for (Integer breakDuration : settings.getBreakDurations()) {
            SchoolDayPeriod period = new SchoolDayPeriod();
            LocalTime startTime = endTime.plusMinutes(breakDuration);
            endTime = startTime.plusMinutes(settings.getLessonDurationMinutes());
            period.setStartTime(startTime);
            period.setEndTime(endTime);
            generatedPeriods.add(period);
        }
        return generatedPeriods;
    }

    @Override
    @Transactional("mainTransactionManager")
    public void saveMany(List<SchoolDayPeriod> periods) {
        repository.save(periods);
    }
}
