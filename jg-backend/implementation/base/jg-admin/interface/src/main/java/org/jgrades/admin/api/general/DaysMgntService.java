/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.api.general;

import org.jgrades.admin.api.model.WorkingDays;

public interface DaysMgntService {
    WorkingDays getWorkingDays();

    void setWorkingDays(WorkingDays workingDays);
}
