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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jgrades.data.api.entities.roles.StudentDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "JG_DATA_SUBGROUP")
@Getter
@Setter
@ToString(exclude = "division")
public class SubGroup implements Serializable {
    public static final String FULL_CLASSGROUP_SUBGROUP_NAME = "_MAIN_SUBGROUP";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIVISION_ID")
    private Division division;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "JG_DATA_SUBGROUP_STUDENT",
            joinColumns = {@JoinColumn(name = "SUBGROUP_ID")},
            inverseJoinColumns = {@JoinColumn(name = "STUDENT_ID")})
    private Set<StudentDetails> members = new HashSet<>();
}
