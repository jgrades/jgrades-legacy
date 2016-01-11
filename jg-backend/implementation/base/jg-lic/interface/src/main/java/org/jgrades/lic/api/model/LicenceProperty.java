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

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "property")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public final class LicenceProperty {
    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlValue
    private String value;
}
