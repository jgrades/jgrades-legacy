package org.jgrades.admin.api.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Selector<T, ID> {
    List<T> getAll();

    Page<T> getPage(Pageable pageable);

    T get(ID id);
}
