/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.api.model;

import lombok.Getter;
import lombok.Setter;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.StudentDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.data.api.utils.RolesMapBuilder;

import java.time.LocalDate;

@Getter
@Setter
public class StudentCsvEntry {
    private String name;

    private String surname;

    private LocalDate dateOfBirth;

    private String nationalIdentificationNumber;

    private String address;

    public User getStudentUser() {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);

        StudentDetails studentDetails = new StudentDetails();
        studentDetails.setDateOfBirth(dateOfBirth);
        studentDetails.setNationalIdentificationNumber(nationalIdentificationNumber);
        studentDetails.setAddress(address);

        RolesMapBuilder rolesMapBuilder = new RolesMapBuilder();
        rolesMapBuilder.addRole(JgRole.STUDENT, studentDetails);
        user.setRoles(rolesMapBuilder.getRoleMap());

        return user;
    }
}
