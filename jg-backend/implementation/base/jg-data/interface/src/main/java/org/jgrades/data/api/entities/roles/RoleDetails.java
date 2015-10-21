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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;

import javax.persistence.*;
import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "role")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AdministratorDetails.class, name = "ADMINISTRATOR"),
        @JsonSubTypes.Type(value = ManagerDetails.class, name = "MANAGER"),
        @JsonSubTypes.Type(value = TeacherDetails.class, name = "TEACHER"),
        @JsonSubTypes.Type(value = StudentDetails.class, name = "STUDENT"),
        @JsonSubTypes.Type(value = ParentDetails.class, name = "PARENT"),
})
@MappedSuperclass
@Getter
@Setter
public abstract class RoleDetails implements Serializable {
        @GenericGenerator(name = "generator", strategy = "foreign",
                parameters = @org.hibernate.annotations.Parameter(name = "property", value = "user"))
        @Id
        @GeneratedValue(generator = "generator")
        private Long id;

        @OneToOne
        @PrimaryKeyJoinColumn
        private User user;

        abstract JgRole roleName();
}
