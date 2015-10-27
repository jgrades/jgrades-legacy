/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.api.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.data.api.model.UserSetting;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.EnumMap;

@Entity
@Table(name = "JG_DATA_USER")
@Getter
@Setter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String login;

    private String name;

    private String surname;

    @UserSetting
    private String email;

    private boolean active = true;

    @Transient
    @JsonDeserialize(keyAs = JgRole.class, contentAs = RoleDetails.class)
    private EnumMap<JgRole, RoleDetails> roles;

    private LocalDateTime lastVisit;
}
