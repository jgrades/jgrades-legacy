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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PagingSpecificationSelector<T, ID> //NOSONAR
        extends PagingSelector<T, ID>, SpecificationSelector<T, ID> { //NOSONAR
    Page<T> getPage(Pageable pageable, Specification<T> specification);
}
