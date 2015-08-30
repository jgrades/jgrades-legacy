package org.jgrades.admin.api.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagingSelector<T, ID> extends Selector<T, ID> {
    Page<T> getPage(Pageable pageable);
}
