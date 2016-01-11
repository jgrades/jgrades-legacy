/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.jgrades.data.api.entities.roles.StudentDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "JG_DATA_CLASS_GROUP")
@Getter
@Setter
public class ClassGroup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SEMESTER_ID", nullable = false)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "YEAR_LEVEL_ID", nullable = false)
    private YearLevel yearLevel;

    private String name;

    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.REMOVE}, mappedBy = "classGroup")
    private List<Division> divisions = Lists.newArrayList();

    @JsonIgnore
    @Transient
    private Set<StudentDetails> members;
}
