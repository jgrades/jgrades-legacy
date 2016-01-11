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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public abstract class AbstractPagingMgntService
        <T, ID extends Serializable, R extends PagingAndSortingRepository<T, ID>> //NOSONAR
        extends AbstractMgntService<T, ID, R> implements CrudPagingService<T, ID> { //NOSONAR
    @Autowired
    public AbstractPagingMgntService(R repository) {
        super(repository);
    }

    @Override
    public Page<T> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
