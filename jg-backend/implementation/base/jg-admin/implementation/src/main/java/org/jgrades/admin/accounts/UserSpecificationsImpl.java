/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.accounts;

import org.jgrades.admin.api.accounts.UserSpecifications;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserSpecificationsImpl implements UserSpecifications {
    @Override
    public Specification<User> withPhrase(String phrase) {
        return null;
    }

    @Override
    public Specification<User> withLogin(String login) {
        return (root, cq, cb) -> cb.equal(root.<String>get("login"), login);
    }

    @Override
    public Specification<User> withName(String name) {
        return (root, cq, cb) -> cb.equal(root.<String>get("name"), name);
    }

    @Override
    public Specification<User> withSurname(String surname) {
        return (root, cq, cb) -> cb.equal(root.<String>get("surname"), surname);
    }

    @Override
    public Specification<User> withEmail(String email) {
        return (root, cq, cb) -> cb.equal(root.<String>get("email"), email);
    }

    @Override
    public Specification<User> onlyActive() {
        return (root, cq, cb) -> cb.isTrue(root.<Boolean>get("active"));
    }

    @Override
    public Specification<User> onlyInactive() {
        return (root, cq, cb) -> cb.isFalse(root.<Boolean>get("active"));
    }

    @Override
    public Specification<User> withRoles(Set<JgRole> set) {
        return (root, cq, cb) -> null;
    }


    @Override
    public Specification<User> lastVisitBetween(DateTime dateTime1, DateTime dateTime2) {
        return (root, cq, cb) -> cb.between(root.get("lastVisit"), dateTime1, dateTime2);
    }
}
