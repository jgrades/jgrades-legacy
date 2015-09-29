package org.jgrades.admin.api.accounts;

import org.jgrades.admin.api.common.CrudPagingService;
import org.jgrades.admin.api.common.PagingSpecificationSelector;
import org.jgrades.data.api.entities.User;

public interface UserMgntService<U extends User> extends CrudPagingService<U, Long>, PagingSpecificationSelector<U, Long> {
}
