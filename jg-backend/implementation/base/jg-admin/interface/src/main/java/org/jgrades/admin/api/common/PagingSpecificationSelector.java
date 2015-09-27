package org.jgrades.admin.api.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PagingSpecificationSelector<T, ID> extends PagingSelector<T, ID>, SpecificationSelector<T, ID> {
    Page<T> getPage(Pageable pageable, Specification<T> specification);
}
