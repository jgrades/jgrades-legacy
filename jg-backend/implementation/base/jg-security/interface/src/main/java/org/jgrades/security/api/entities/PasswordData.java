/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.api.entities;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_SECURITY_PASSWORD_DATA")
@Data
public class PasswordData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    private String password;

    @Column
    @Type(type = CustomType.JODA_DATE_TIME)
    private DateTime lastChange;

    @Column
    @Type(type = CustomType.JODA_DATE_TIME)
    private DateTime lockingDateTime;

    private int failedLoginAmount;

    public void increaseFailedLoginAmount() {
        failedLoginAmount++;
    }
}
