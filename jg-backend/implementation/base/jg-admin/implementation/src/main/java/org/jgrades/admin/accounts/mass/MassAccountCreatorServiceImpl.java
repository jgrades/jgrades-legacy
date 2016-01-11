/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.accounts.mass;

import com.google.common.collect.Sets;
import org.jgrades.admin.api.accounts.MassAccountCreatorService;
import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.admin.api.model.*;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.StudentDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.security.api.service.PasswordMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MassAccountCreatorServiceImpl implements MassAccountCreatorService {
    @Autowired
    private UserMgntService userMgntService;

    @Autowired
    private PasswordMgntService passwordMgntService;

    @Autowired
    private LoginGenerationStrategy loginGenerator;

    @Autowired
    private PasswordGenerationStrategy passwordGenerator;

    @Override
    public Set<MassAccountCreatorResultRecord> createStudents(Set<StudentCsvEntry> students, StudentMassCreatingSettings settings) {
        Set<MassAccountCreatorResultRecord> resultRecords = Sets.newHashSet();
        resultRecords.addAll(
                students.stream()
                        .map(student -> processSingleStudent(student, settings))
                        .collect(Collectors.toList())
        );
        return resultRecords;
    }

    //TODO adding student to classgroup
    private MassAccountCreatorResultRecord processSingleStudent(StudentCsvEntry student, StudentMassCreatingSettings settings) {
        User user = student.getStudentUser();
        user.setLogin(loginGenerator.generateLogin(user));
        user.setActive(settings.isActiveAfterCreation());
        userMgntService.saveOrUpdate(user);
        User persistStudent = userMgntService.getWithLogin(user.getLogin());
        String studentPassword = passwordGenerator.getPassword();
        passwordMgntService.setPassword(studentPassword, persistStudent);

        StudentDetails studentDetails = (StudentDetails) persistStudent.getRoles().get(JgRole.STUDENT);
        User persistParent = studentDetails.getParent().getUser();
        persistParent.setActive(settings.isActiveAfterCreation());
        String parentPassword = passwordGenerator.getPassword();
        passwordMgntService.setPassword(parentPassword, persistParent);

        return new MassAccountCreatorResultRecord(
                student, persistStudent.getLogin(), studentPassword,
                persistParent.getLogin(), parentPassword);
    }
}
