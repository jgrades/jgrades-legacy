package org.jgrades.rest.admin.accounts;

import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.data.api.entities.User;
import org.jgrades.rest.admin.common.AbstractRestCrudPagingService;

public abstract class AbstractUserService<U extends User> extends AbstractRestCrudPagingService<U, Long, UserMgntService<U>> {
    protected AbstractUserService(UserMgntService<U> crudService) {
        super(crudService);
    }
}
