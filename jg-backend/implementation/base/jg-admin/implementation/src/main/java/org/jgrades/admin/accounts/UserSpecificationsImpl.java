/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.accounts;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jgrades.admin.api.accounts.UserSpecifications;
import org.jgrades.admin.api.exception.SearchEngineException;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.*;
import org.jgrades.data.api.model.JgRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
public class UserSpecificationsImpl implements UserSpecifications {
    @Value("${admin.minimum.search.text.length}")
    protected Integer minimumTextLength;

    private static Predicate getSearchPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb, Class clazz) {
        Subquery subquery = cq.subquery(clazz);
        Root subRoot = subquery.from(clazz);
        subquery.select(subRoot);
        Predicate predicate = cb.equal(subRoot.get("id"), root.get("id"));
        subquery.where(predicate);
        return cb.exists(subquery);
    }

    @Override
    public Specification<User> withPhrase(String phrase) {
        checkIsNotTooShort(phrase);
        return Specifications.where(withLogin(phrase))
                .or(withName(phrase))
                .or(withSurname(phrase))
                .or(withEmail(phrase));
    }

    @Override
    public Specification<User> withLogin(String login) {
        checkIsNotTooShort(login);
        return (root, cq, cb) -> cb.like(root.<String>get("login"), login);
    }

    @Override
    public Specification<User> withName(String name) {
        checkIsNotTooShort(name);
        return (root, cq, cb) -> cb.like(root.<String>get("name"), name);
    }

    @Override
    public Specification<User> withSurname(String surname) {
        checkIsNotTooShort(surname);
        return (root, cq, cb) -> cb.like(root.<String>get("surname"), surname);
    }

    @Override
    public Specification<User> withEmail(String email) {
        checkIsNotTooShort(email);
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

    @Override
    public Specification<User> lastVisitBetween(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        return (root, cq, cb) -> cb.between(root.get("lastVisit"), dateTime1, dateTime2);
    }

    private void checkIsNotTooShort(String string) {
        if (StringUtils.isEmpty(string) || string.length() < minimumTextLength) {
            throw new SearchEngineException("Value " + string + " is too short");
        }
    }
}
