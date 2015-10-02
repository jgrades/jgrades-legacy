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
import org.jgrades.data.api.model.roles.Roles;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component//TODO
public class UserSpecificationsImpl<U extends User> implements UserSpecifications<U> {
    @Override
    public Specification<U> withPhrase(String phrase) {
        return null;
    }

    @Override
    public Specification<U> withLogin(String login) {
        return (root, cq, cb) -> cb.equal(root.<String>get("login"), login);
    }

    @Override
    public Specification<U> withName(String name) {
        return (root, cq, cb) -> cb.equal(root.<String>get("name"), name);
    }

    @Override
    public Specification<U> withSurname(String surname) {
        return (root, cq, cb) -> cb.equal(root.<String>get("surname"), surname);
    }

    @Override
    public Specification<U> withEmail(String email) {
        return (root, cq, cb) -> cb.equal(root.<String>get("email"), email);
    }

    @Override
    public Specification<U> onlyActive() {
        return (root, cq, cb) -> cb.isTrue(root.<Boolean>get("active"));
    }

    @Override
    public Specification<U> onlyInactive() {
        return (root, cq, cb) -> cb.isFalse(root.<Boolean>get("active"));
    }

    @Override
    public Specification<U> withRoles(Roles roles) {
        return null;
    }

    @Override
    public Specification<U> lastVisitBetween(DateTime dateTime1, DateTime dateTime2) {
        return null;
    }
}
