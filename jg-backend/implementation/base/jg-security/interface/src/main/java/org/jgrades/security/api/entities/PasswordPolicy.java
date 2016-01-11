/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.api.entities;

import lombok.Data;
import org.jgrades.data.api.model.JgRole;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_SECURITY_PASSWORD_POLICY")
@Data
public class PasswordPolicy implements Serializable {
    @Id
    @Enumerated(EnumType.STRING)
    private JgRole jgRole;

    private Integer expirationDays;

    private Integer minimumLength;

    private Integer maximumNumberOfFailedLogin;
}
