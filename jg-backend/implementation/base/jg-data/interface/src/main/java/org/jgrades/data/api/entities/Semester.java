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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JG_DATA_SEMESTER")
@Data
public class Semester implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEMESTER_ID", unique = true, nullable = false)
    private Long id;

    private String name;

    private boolean active;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACADEMIC_YEAR_ID", nullable = false)
    private AcademicYear academicYear;

    @JsonIgnore
    @OneToMany(mappedBy = "pk.yearLevel", cascade = CascadeType.ALL)
    private List<SemesterYearLevel> yearLevels = Lists.newArrayList();
}
