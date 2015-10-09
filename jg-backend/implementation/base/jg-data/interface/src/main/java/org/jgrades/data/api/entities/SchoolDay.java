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

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;

@Entity
@Table(name = "JG_DATA_SCHOOL_DAY")
@Getter
@Setter
public class SchoolDay implements Serializable {
    @Id
    private Integer ordinalNumber;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    private School school;

    public SchoolDay() { //NOSONAR (JPA needs default constructor)
    }

    public SchoolDay(Integer ordinalNumber, DayOfWeek dayOfWeek) {
        this.ordinalNumber = ordinalNumber;
        this.dayOfWeek = dayOfWeek;
    }
}
