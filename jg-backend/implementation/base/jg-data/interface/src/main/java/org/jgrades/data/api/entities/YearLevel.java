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
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JG_DATA_YEAR_LEVEL")
@Getter
@Setter
public class YearLevel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "YEAR_LEVEL_ID", unique = true, nullable = false)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "pk.semester", cascade = CascadeType.ALL)
    private List<SemesterYearLevel> semesters = Lists.newArrayList();
}
