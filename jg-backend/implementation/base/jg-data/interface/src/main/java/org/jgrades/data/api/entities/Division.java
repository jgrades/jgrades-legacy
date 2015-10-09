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
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JG_DATA_DIVISION")
@Getter
@Setter
@ToString(exclude = "classGroup")
public class Division implements Serializable {
    public static final String FULL_CLASSGROUP_DIVISION_NAME = "_DEFAULT_DIVISION";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLASS_GROUP_ID", nullable = false)
    private ClassGroup classGroup;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL, CascadeType.REMOVE}, mappedBy = "division")
    private List<SubGroup> subGroups = Lists.newArrayList();
}
