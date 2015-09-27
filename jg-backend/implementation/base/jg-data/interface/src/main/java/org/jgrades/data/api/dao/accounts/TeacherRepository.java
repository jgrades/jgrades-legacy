package org.jgrades.data.api.dao.accounts;

import org.jgrades.data.api.dao.AbstaractUserRepository;
import org.jgrades.data.api.entities.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends AbstaractUserRepository<Teacher> {
}
