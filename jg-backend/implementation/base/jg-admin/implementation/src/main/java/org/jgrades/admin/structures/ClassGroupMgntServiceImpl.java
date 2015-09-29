package org.jgrades.admin.structures;

import org.jgrades.admin.api.structures.ClassGroupMgntService;
import org.jgrades.admin.common.AbstractPagingMgntService;
import org.jgrades.data.api.dao.structures.ClassGroupRepository;
import org.jgrades.data.api.entities.ClassGroup;
import org.jgrades.data.api.entities.Division;
import org.jgrades.data.api.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ClassGroupMgntServiceImpl extends AbstractPagingMgntService<ClassGroup, Long, ClassGroupRepository> implements ClassGroupMgntService {
    @Autowired
    public ClassGroupMgntServiceImpl(ClassGroupRepository repository) {
        super(repository);
    }

    @Override
    @Transactional("mainTransactionManager")
    public List<Division> getDivisions(ClassGroup classGroup) {
        return getWithId(classGroup.getId()).getDivisions();
    }

    @Override
    @Transactional("mainTransactionManager")//TODO
    public Set<Student> getStudents(ClassGroup classGroup) {
        return null;
    }

    @Override//TODO
    public void addStudent(ClassGroup classGroup, Student student) {
    }
}
