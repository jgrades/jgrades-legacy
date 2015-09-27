package org.jgrades.data.api.dao.accounts;

import org.jgrades.data.api.dao.AbstaractUserRepository;
import org.jgrades.data.api.entities.Parent;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends AbstaractUserRepository<Parent> {
}
