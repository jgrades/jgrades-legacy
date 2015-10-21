/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.api.general;

import org.jgrades.admin.api.model.PeriodsGeneratorSettings;
import org.jgrades.data.api.entities.SchoolDayPeriod;
import org.jgrades.data.api.service.crud.CrudService;

import java.util.List;

public interface PeriodsMgntService extends CrudService<SchoolDayPeriod, Long> {
    void saveMany(List<SchoolDayPeriod> periods);

    List<SchoolDayPeriod> generateManyWithGenerator(PeriodsGeneratorSettings settings);
}
