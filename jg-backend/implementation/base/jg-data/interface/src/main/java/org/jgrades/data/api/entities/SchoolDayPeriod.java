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
import org.hibernate.annotations.Type;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "JG_DATA_SCHOOL_DAY_PERIOD")
@Getter
@Setter
public class SchoolDayPeriod implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Type(type = CustomType.JODA_LOCAL_TIME)
    private LocalTime startTime;

    @Column
    @Type(type = CustomType.JODA_LOCAL_TIME)
    private LocalTime endTime;
}
