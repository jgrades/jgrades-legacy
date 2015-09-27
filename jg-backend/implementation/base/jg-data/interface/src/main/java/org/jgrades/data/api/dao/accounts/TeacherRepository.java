package org.jgrades.data.api.dao.accounts;

import org.jgrades.data.api.dao.RoleUserRepository;
import org.jgrades.data.api.entities.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends RoleUserRepository<Teacher> {
}
