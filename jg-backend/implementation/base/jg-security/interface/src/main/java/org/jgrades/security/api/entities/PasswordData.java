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
import org.jgrades.data.api.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    private LocalDateTime lastChange;

    @Column
    private LocalDateTime lockingDateTime;

    private int failedLoginAmount;

    public void increaseFailedLoginAmount() {
        failedLoginAmount++;
    }
}
