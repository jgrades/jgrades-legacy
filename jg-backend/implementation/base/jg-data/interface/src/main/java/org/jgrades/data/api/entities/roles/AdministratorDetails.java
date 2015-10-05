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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;

import javax.persistence.*;

@Entity
@Table(name = "JG_DATA_ADMINISTRATOR_DETAILS")
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
public class AdministratorDetails implements RoleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private User user;

    @Override
    public JgRole roleName() {
        return JgRole.ADMINISTRATOR;
    }
}
