/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.admin.accounts;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.admin.api.accounts.UserSpecifications;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserSpecificationsBuilder {
    @Autowired
    private UserSpecifications specs;

    private Specification<User> phraseSpec;

    private Specification<User> result;

    public UserSpecificationsBuilder withPhrase(String phrase) {
        if (StringUtils.isNotEmpty(phrase)) {
            phraseSpec = Specifications.where(specs.withPhrase(phrase));
        }
        return this;
    }

    public UserSpecificationsBuilder withoutPhrase() {
        phraseSpec = null;
        return this;
    }

    public UserSpecificationsBuilder withLogin(String login) {
        if (StringUtils.isNotEmpty(login)) {
            result = (result == null)
                    ? Specifications.where(specs.withLogin(login))
                    : Specifications.where(result).and(specs.withLogin(login));
        }
        return this;
    }

    public UserSpecificationsBuilder withName(String name) {
        if (StringUtils.isNotEmpty(name)) {
            result = (result == null)
                    ? Specifications.where(specs.withName(name))
                    : Specifications.where(result).and(specs.withName(name));
        }
        return this;
    }

    public UserSpecificationsBuilder withSurname(String surname) {
        if (StringUtils.isNotEmpty(surname)) {
            result = (result == null)
                    ? Specifications.where(specs.withSurname(surname))
                    : Specifications.where(result).and(specs.withSurname(surname));
        }
        return this;
    }

    public UserSpecificationsBuilder withEmail(String email) {
        if (StringUtils.isNotEmpty(email)) {
            result = (result == null)
                    ? Specifications.where(specs.withEmail(email))
                    : Specifications.where(result).and(specs.withEmail(email));
        }
        return this;
    }

    public UserSpecificationsBuilder withActiveState(Boolean active) {
        if (active == null) {
            return this;
        } else if (active.equals(Boolean.TRUE)) {
            result = (result == null)
                    ? Specifications.where(specs.onlyActive())
                    : Specifications.where(result).and(specs.onlyActive());
        } else {
            result = (result == null)
                    ? Specifications.where(specs.onlyInactive())
                    : Specifications.where(result).and(specs.onlyInactive());
        }
        return this;
    }

    public UserSpecificationsBuilder withRoles(String rolesString) {
        if (StringUtils.isNotEmpty(rolesString)) {
            String[] rolesStringArr = rolesString.split(",");
            Set<JgRole> roles = Sets.newHashSet();
            for (String role : rolesStringArr) {
                roles.add(JgRole.valueOf(role));
            }
            result = (result == null)
                    ? Specifications.where(specs.withRoles(roles))
                    : Specifications.where(result).and(specs.withRoles(roles));
        }
        return this;
    }

    public UserSpecificationsBuilder withLastVisitBetween(DateTime dateTime1, DateTime dateTime2) {
        if (dateTime1 == null && dateTime2 == null) {
            return this;
        }

        if (dateTime1 == null) {
            dateTime1 = new DateTime(0);
        }
        if (dateTime2 == null) {
            dateTime2 = DateTime.now();
        }
        result = (result == null)
                ? Specifications.where(specs.lastVisitBetween(dateTime1, dateTime2))
                : Specifications.where(result).and(specs.lastVisitBetween(dateTime1, dateTime2));
        return this;
    }

    public Specification<User> build() {
        try {
            return (phraseSpec != null) ? phraseSpec : result;
        } finally {
            phraseSpec = null;
            result = null;
        }
    }
}
