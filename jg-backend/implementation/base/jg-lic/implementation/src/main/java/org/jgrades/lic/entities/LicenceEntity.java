/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.entities;

import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "JG_LIC_LICENCE")
@Getter
@Setter
@EqualsAndHashCode(of = "uid")
public class LicenceEntity implements Serializable {
    @Id
    private Long uid;

    @OneToOne(cascade = CascadeType.ALL)
    private CustomerEntity customer;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductEntity product;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LicencePropertyEntity> properties = Lists.<LicencePropertyEntity>newArrayList();

    private String licenceFilePath;

    private String signatureFilePath;
}
