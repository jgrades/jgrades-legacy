package org.jgrades.admin.general;

import org.jgrades.admin.api.general.ClassroomMgntService;
import org.jgrades.admin.common.AbstractPagingMgntService;
import org.jgrades.data.api.dao.ClassroomRepository;
import org.jgrades.data.api.entities.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomMgntServiceImpl extends AbstractPagingMgntService<Classroom, Long, ClassroomRepository> implements ClassroomMgntService {
    @Autowired
    public ClassroomMgntServiceImpl(ClassroomRepository repository) {
        super(repository);
    }
}
