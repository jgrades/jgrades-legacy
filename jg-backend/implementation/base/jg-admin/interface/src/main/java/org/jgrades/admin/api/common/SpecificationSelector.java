package org.jgrades.admin.api.common;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SpecificationSelector<T, ID> extends Selector<T, ID> {
    List<T> get(Specification<T> specification);
}
