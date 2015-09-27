package org.jgrades.data.api.dao.accounts;

import org.jgrades.data.api.dao.AbstaractUserRepository;
import org.jgrades.data.api.entities.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends AbstaractUserRepository<Student> {
}
