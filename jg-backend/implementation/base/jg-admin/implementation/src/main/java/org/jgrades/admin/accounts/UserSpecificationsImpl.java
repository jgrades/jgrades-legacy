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

import com.google.common.collect.Lists;
import org.jgrades.admin.api.accounts.UserSpecifications;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.*;
import org.jgrades.data.api.model.JgRole;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;

@Component
public class UserSpecificationsImpl implements UserSpecifications {
    @Override
    public Specification<User> withPhrase(String phrase) {
        return Specifications.where(withLogin(phrase))
                .or(withName(phrase))
                .or(withSurname(phrase))
                .or(withEmail(phrase));
    }

    @Override
    public Specification<User> withLogin(String login) {
        return (root, cq, cb) -> cb.like(root.<String>get("login"), login);
    }

    @Override
    public Specification<User> withName(String name) {
        return (root, cq, cb) -> cb.like(root.<String>get("name"), name);
    }

    @Override
    public Specification<User> withSurname(String surname) {
        return (root, cq, cb) -> cb.like(root.<String>get("surname"), surname);
    }

    @Override
    public Specification<User> withEmail(String email) {
        return (root, cq, cb) -> cb.like(root.<String>get("email"), email);
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
    public Specification<User> withRoles(Set<JgRole> roleSet) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (roleSet.contains(JgRole.ADMINISTRATOR)) {
                predicates.add(getSearchPredicate(root, cq, cb, AdministratorDetails.class));
            }
            if (roleSet.contains(JgRole.MANAGER)) {
                predicates.add(getSearchPredicate(root, cq, cb, ManagerDetails.class));
            }
            if (roleSet.contains(JgRole.TEACHER)) {
                predicates.add(getSearchPredicate(root, cq, cb, TeacherDetails.class));
            }
            if (roleSet.contains(JgRole.STUDENT)) {
                predicates.add(getSearchPredicate(root, cq, cb, StudentDetails.class));
            }
            if (roleSet.contains(JgRole.PARENT)) {
                predicates.add(getSearchPredicate(root, cq, cb, ParentDetails.class));
            }
            return cb.or(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private Predicate getSearchPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb, Class clazz) {
        Subquery administratorQuery = cq.subquery(clazz);
        Root rootAdmin = administratorQuery.from(clazz);
        administratorQuery.select(rootAdmin);
        Predicate adminPredicate = cb.equal(rootAdmin.get("id"), root.get("id"));
        administratorQuery.where(adminPredicate);
        return cb.exists(administratorQuery);
    }


    @Override
    public Specification<User> lastVisitBetween(DateTime dateTime1, DateTime dateTime2) {
        return (root, cq, cb) -> cb.between(root.get("lastVisit"), dateTime1, dateTime2);
    }
}
