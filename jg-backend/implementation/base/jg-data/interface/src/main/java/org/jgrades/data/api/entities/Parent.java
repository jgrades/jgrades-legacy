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

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_DATA_PARENT")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Data
public class Parent extends User implements Serializable {
    private String contactPhone;

    private String address;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private Student student;
}
