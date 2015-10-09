/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.api.dao;

import org.jgrades.security.api.entities.PasswordData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordDataRepository extends CrudRepository<PasswordData, Long> {
    @Query("select pd from PasswordData pd where pd.user.login = ?1")
    PasswordData getPasswordDataWithUser(String login);
}
