package org.jgrades.admin.api.accounts;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.admin.api.common.PagingSelector;
import org.jgrades.data.api.entities.User;

public interface AccountService extends Manager<User, Long>, PagingSelector<User, Long> {
}
