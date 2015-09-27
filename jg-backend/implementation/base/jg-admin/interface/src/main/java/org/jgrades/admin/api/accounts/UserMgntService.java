package org.jgrades.admin.api.accounts;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.admin.api.common.PagingSpecificationSelector;
import org.jgrades.data.api.entities.User;

public interface UserMgntService<U extends User> extends Manager<U>, PagingSpecificationSelector<U, Long> {
}
