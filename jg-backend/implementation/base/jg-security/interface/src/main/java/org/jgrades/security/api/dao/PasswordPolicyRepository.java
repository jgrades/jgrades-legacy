package org.jgrades.security.api.dao;

import org.jgrades.data.api.model.roles.JgRole;
import org.jgrades.security.api.entities.PasswordPolicy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordPolicyRepository extends CrudRepository<PasswordPolicy, JgRole> {
}
