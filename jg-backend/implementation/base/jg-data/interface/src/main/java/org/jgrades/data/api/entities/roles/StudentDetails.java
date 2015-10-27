/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.api.entities.roles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.data.api.model.UserSetting;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "JG_DATA_STUDENT_DETAILS")
@Getter
@Setter
@ToString(exclude="parent")
public class StudentDetails extends RoleDetails {
    @UserSetting
    private String contactPhone;

    private LocalDate dateOfBirth;

    private String nationalIdentificationNumber;

    @UserSetting
    private String address;

    @OneToOne
    @JoinColumn(name = "parent_user_id")
    @JsonIgnore
    private ParentDetails parent;

    @Override
    public JgRole roleName() {
        return JgRole.STUDENT;
    }
}
