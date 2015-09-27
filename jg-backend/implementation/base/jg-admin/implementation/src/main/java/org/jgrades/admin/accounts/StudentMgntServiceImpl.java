package org.jgrades.admin.accounts;

import org.jgrades.admin.api.accounts.StudentMgntService;
import org.jgrades.data.api.dao.accounts.StudentRepository;
import org.jgrades.data.api.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentMgntServiceImpl extends AbstractUserMgntServiceImpl<Student, StudentRepository> implements StudentMgntService {
    @Autowired
    public StudentMgntServiceImpl(StudentRepository repository) {
        super(repository);
    }
}
