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

import lombok.Data;
import org.jgrades.data.api.model.roles.ManagerDetails;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "JG_DATA_MANAGER")
@PrimaryKeyJoinColumn(name = "USER_ID")
@Data
public class Manager extends User implements ManagerDetails, Serializable {
}
