package org.jgrades.data.api.dao.accounts;

import org.jgrades.data.api.dao.AbstaractUserRepository;
import org.jgrades.data.api.entities.Manager;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends AbstaractUserRepository<Manager> {
}
