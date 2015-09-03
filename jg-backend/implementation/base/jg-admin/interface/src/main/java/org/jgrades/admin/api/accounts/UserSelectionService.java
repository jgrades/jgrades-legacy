package org.jgrades.admin.api.accounts;

import org.jgrades.admin.api.common.PagingSelector;
import org.jgrades.data.api.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserSelectionService<U extends User> extends PagingSelector<U, Long> {
    List<U> get(Specification<U> specification);

    Page<U> getPsge(Pageable pageable, Specification<U> specification);
}
