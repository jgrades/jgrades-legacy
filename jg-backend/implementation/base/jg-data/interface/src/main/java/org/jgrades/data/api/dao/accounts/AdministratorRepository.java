package org.jgrades.data.api.dao.accounts;

import org.jgrades.data.api.dao.AbstaractUserRepository;
import org.jgrades.data.api.entities.Administrator;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends AbstaractUserRepository<Administrator> {
}
