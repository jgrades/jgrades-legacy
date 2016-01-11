/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.api.service.crud;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Service
public abstract class AbstractMgntService<T, ID extends Serializable, //NOSONAR
        R extends CrudRepository<T, ID>> implements CrudService<T, ID> { //NOSONAR
    protected final R repository;

    @Autowired
    public AbstractMgntService(R repository) {
        this.repository = repository;
    }

    @Override
    public void saveOrUpdate(T obj) {
        repository.save(obj);
    }

    @Override
    public void remove(T obj) {
        repository.delete(obj);
    }

    @Override
    public void remove(List<T> objs) {
        repository.delete(objs);
    }

    @Override
    public void removeId(ID id) {
        if (repository.exists(id)) {
            repository.delete(id);
        }
    }

    @Override
    @Transactional("mainTransactionManager")
    public void removeIds(List<ID> ids) {
        ids.forEach(repository::delete);
    }

    @Override
    public List<T> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public T getWithId(ID id) {
        return repository.findOne(id);
    }

    @Override
    public List<T> getWithIds(List<ID> ids) {
        return Lists.newArrayList(repository.findAll(ids));
    }
}
