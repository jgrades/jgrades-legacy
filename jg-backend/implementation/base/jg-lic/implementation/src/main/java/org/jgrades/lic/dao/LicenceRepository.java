/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.lic.dao;

import org.jgrades.lic.entities.LicenceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenceRepository extends CrudRepository<LicenceEntity, Long> {
    @Query("select l from LicenceEntity l where l.product.name = ?1")
    List<LicenceEntity> findByProductName(String name);
}
