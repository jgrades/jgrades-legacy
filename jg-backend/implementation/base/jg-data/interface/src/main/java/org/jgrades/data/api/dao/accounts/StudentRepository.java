package org.jgrades.data.api.dao.accounts;

import org.jgrades.data.api.dao.RoleUserRepository;
import org.jgrades.data.api.entities.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends RoleUserRepository<Student> {
}
