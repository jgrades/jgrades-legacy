/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.data.api.service.crud;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SpecificationSelector<T, ID> extends Selector<T, ID> { //NOSONAR
    List<T> get(Specification<T> specification);
}
