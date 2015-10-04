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
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_DATA_STUDENT")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Data
@EqualsAndHashCode
public class Student extends User implements Serializable {
    private String contactPhone;

    @Column
    @Type(type = CustomType.JODA_LOCAL_DATE)
    private LocalDate dateOfBirth;

    private String nationalIdentificationNumber;

    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Parent parent;
}
