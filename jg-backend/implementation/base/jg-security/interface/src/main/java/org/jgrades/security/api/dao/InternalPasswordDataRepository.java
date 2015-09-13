package org.jgrades.security.api.dao;

import org.jgrades.security.api.entities.PasswordData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("internalPasswordDataRepository")
public interface InternalPasswordDataRepository extends CrudRepository<PasswordData, Long> {
}
