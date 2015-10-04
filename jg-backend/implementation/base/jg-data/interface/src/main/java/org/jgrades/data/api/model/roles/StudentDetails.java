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

import lombok.Data;
import org.jgrades.data.api.entities.Parent;
import org.joda.time.LocalDate;

@Data
public class StudentDetails implements RoleDetails {
    private String contactPhone;

    private LocalDate dateOfBirth;

    private String nationalIdentificationNumber;

    private String address;

    private Parent parent;
}
