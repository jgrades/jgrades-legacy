/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.api.common;

import java.util.List;

public interface RestCrudService<T, ID> {
    void insertOrUpdate(T entity);

    void remove(List<ID> ids);

    T getWithId(ID id);

    List<T> getWithIds(List<ID> ids);

    List<T> getAll();
}
