/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.api.accounts;

import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Set;

public interface UserSpecifications {
    Specification<User> withPhrase(String phrase);

    Specification<User> withLogin(String login);

    Specification<User> withName(String name);

    Specification<User> withSurname(String surname);

    Specification<User> withEmail(String email);

    Specification<User> onlyActive();

    Specification<User> onlyInactive();

    Specification withRoles(Set<JgRole> roles);

    Specification<User> lastVisitBetween(LocalDateTime dateTime1, LocalDateTime dateTime2);
}
