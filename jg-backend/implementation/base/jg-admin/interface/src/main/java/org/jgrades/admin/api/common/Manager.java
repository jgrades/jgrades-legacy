/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.api.common;

import java.util.List;

public interface Manager<T, ID> { //NOSONAR
    void saveOrUpdate(T obj);

    void remove(T obj);

    void remove(List<T> objs);

    void removeId(ID id);

    void removeIds(List<ID> ids);
}
