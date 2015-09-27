package org.jgrades.data.api.dao.accounts;

import org.jgrades.data.api.dao.AbstaractUserRepository;
import org.jgrades.data.api.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface GenericUserRepository extends AbstaractUserRepository<User> {

}
