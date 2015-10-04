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

import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.admin.common.AbstractPagingMgntService;
import org.jgrades.data.api.dao.AbstaractUserRepository;
import org.jgrades.data.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class AbstractUserMgntServiceImpl<U extends User, R extends AbstaractUserRepository<U>> extends AbstractPagingMgntService<U, Long, R> implements UserMgntService<U> {
    @Autowired
    private UserModelEnrichment<U> userModelEnrichment;

    @Autowired
    public AbstractUserMgntServiceImpl(R repository) {
        super(repository);
    }

    @Override
    public List<U> getAll() {
        return super.getAll().stream()
                .map(user -> userModelEnrichment.enrichWithRoles(user))
                .collect(Collectors.toList());
    }

    @Override
    public U getWithId(Long id) {
        return userModelEnrichment.enrichWithRoles(super.getWithId(id));
    }

    @Override
    public List<U> getWithIds(List<Long> ids) {
        return super.getWithIds(ids).stream()
                .map(user -> userModelEnrichment.enrichWithRoles(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<U> get(Specification<U> specification) {
        return repository.findAll(specification).stream()
                .map(user -> userModelEnrichment.enrichWithRoles(user))
                .collect(Collectors.toList());
    }

    @Override
    public Page<U> getPage(Pageable pageable) {
        Page<U> pageBeforeEnrichment = super.getPage(pageable);
        List<U> newContent = pageBeforeEnrichment.getContent().stream()
                .map(user -> userModelEnrichment.enrichWithRoles(user))
                .collect(Collectors.toList());
        return new PageImpl<U>(newContent, pageable, pageBeforeEnrichment.getTotalElements());
    }

    @Override
    public Page<U> getPage(Pageable pageable, Specification<U> specification) {
        Page<U> pageBeforeEnrichment = repository.findAll(specification, pageable);
        List<U> newContent = pageBeforeEnrichment.getContent().stream()
                .map(user -> userModelEnrichment.enrichWithRoles(user))
                .collect(Collectors.toList());
        return new PageImpl<U>(newContent, pageable, pageBeforeEnrichment.getTotalElements());
    }
}
