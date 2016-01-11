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

import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.data.api.dao.accounts.UserRepository;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.data.api.service.crud.AbstractPagingMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMgntServiceImpl extends AbstractPagingMgntService<User, Long, UserRepository> implements UserMgntService {
    @Autowired
    private UserModelEnrichment userModelEnrichment;

    @Autowired
    private UserRolesProcessor userRolesProcessor;

    @Autowired
    public UserMgntServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    @Transactional("mainTransactionManager")
    public void saveOrUpdate(User user) {
        user.setId(repository.save(user).getId());
        userRolesProcessor.process(user);
    }

    @Override
    public void remove(User user) {
        remove(getWithId(user.getId()));
    }

    @Override
    @Transactional("mainTransactionManager")
    public void remove(List<User> users) {
        users.forEach(this::remove);
    }

    @Override
    @Transactional("mainTransactionManager")
    public void removeIds(List<Long> ids) {
        ids.forEach(this::removeId);
    }

    @Override
    @Transactional("mainTransactionManager")
    public void removeId(Long id) {
        userRolesProcessor.remove(getWithId(id));
        super.removeId(id);
    }

    @Override
    public List<User> getAll() {
        return super.getAll().stream()
                .map(userModelEnrichment::enrichWithRoles)
                .collect(Collectors.toList());
    }

    @Override
    public User getWithId(Long id) {
        return userModelEnrichment.enrichWithRoles(super.getWithId(id));
    }

    @Override
    public List<User> getWithIds(List<Long> ids) {
        return super.getWithIds(ids).stream()
                .map(userModelEnrichment::enrichWithRoles)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> get(Specification<User> specification) {
        return repository.findAll(specification).stream()
                .map(userModelEnrichment::enrichWithRoles)
                .collect(Collectors.toList());
    }

    @Override
    public Page<User> getPage(Pageable pageable) {
        Page<User> pageBeforeEnrichment = super.getPage(pageable);
        List<User> newContent = pageBeforeEnrichment.getContent().stream()
                .map(userModelEnrichment::enrichWithRoles)
                .collect(Collectors.toList());
        return new PageImpl<User>(newContent, pageable, pageBeforeEnrichment.getTotalElements());
    }

    @Override
    public Page<User> getPage(Pageable pageable, Specification<User> specification) {
        Page<User> pageBeforeEnrichment = repository.findAll(specification, pageable);
        List<User> newContent = pageBeforeEnrichment.getContent().stream()
                .map(userModelEnrichment::enrichWithRoles)
                .collect(Collectors.toList());
        return new PageImpl<User>(newContent, pageable, pageBeforeEnrichment.getTotalElements());
    }

    @Override
    public Set<JgRole> getUserRoles(User user) {
        return userModelEnrichment.getRoles(user);
    }

    @Override
    public User getWithLogin(String login) {
        return userModelEnrichment.enrichWithRoles(repository.findFirstByLogin(login));
    }
}
