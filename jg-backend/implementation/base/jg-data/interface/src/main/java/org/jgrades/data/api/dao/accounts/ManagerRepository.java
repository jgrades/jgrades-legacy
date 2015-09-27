package org.jgrades.data.api.dao.accounts;

import org.jgrades.data.api.dao.RoleUserRepository;
import org.jgrades.data.api.entities.Manager;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends RoleUserRepository<Manager> {
}
