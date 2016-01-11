/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.api.model;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "licence")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"customer", "product", "properties"})
@Data
public final class Licence {
    @XmlAttribute(name = "uid", required = true)
    private Long uid;

    @XmlElement(name = "customer", required = true)
    private Customer customer;

    @XmlElement(name = "product", required = true)
    private Product product;

    @XmlElement(name = "property")
    @XmlElementWrapper(name = "properties")
    private List<LicenceProperty> properties = Lists.newArrayList();
}
