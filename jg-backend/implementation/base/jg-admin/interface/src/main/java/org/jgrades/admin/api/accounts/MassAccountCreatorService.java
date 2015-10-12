/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.api.accounts;

import org.jgrades.admin.api.model.MassAccountCreatorResultRecord;
import org.jgrades.admin.api.model.StudentCsvEntry;
import org.jgrades.admin.api.model.StudentMassCreatingSettings;

import java.util.Set;

public interface MassAccountCreatorService {
    Set<MassAccountCreatorResultRecord> createStudents(Set<StudentCsvEntry> students,
                                                       StudentMassCreatingSettings settings);
}
