package org.jgrades.admin.accounts;

import org.jgrades.admin.api.accounts.TeacherMgntService;
import org.jgrades.data.api.dao.accounts.TeacherRepository;
import org.jgrades.data.api.entities.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherMgntServiceImpl extends AbstractUserMgntServiceImpl<Teacher, TeacherRepository> implements TeacherMgntService {
    @Autowired
    public TeacherMgntServiceImpl(TeacherRepository repository) {
        super(repository);
    }
}
