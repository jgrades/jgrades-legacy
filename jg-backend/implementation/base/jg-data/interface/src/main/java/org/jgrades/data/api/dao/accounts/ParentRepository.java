package org.jgrades.data.api.dao.accounts;

import org.jgrades.data.api.dao.RoleUserRepository;
import org.jgrades.data.api.entities.Parent;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends RoleUserRepository<Parent> {
}
