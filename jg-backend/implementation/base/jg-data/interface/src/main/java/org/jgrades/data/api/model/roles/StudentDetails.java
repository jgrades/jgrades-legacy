/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.api.model.roles;

import org.jgrades.data.api.entities.Parent;
import org.joda.time.LocalDate;

public interface StudentDetails extends RoleDetails {
    String getContactPhone();

    String getAddress();

    LocalDate getDateOfBirth();

    String getNationalIdentificationNumber();

    Parent getParent();
}
