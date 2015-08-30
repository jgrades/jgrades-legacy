package org.jgrades.admin.general;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.general.ClassroomMgntService;
import org.jgrades.data.api.entities.Classroom;
import org.jgrades.data.dao.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomMgntServiceImpl implements ClassroomMgntService {
    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public void saveOrUpdate(Classroom classroom) {
        classroomRepository.save(classroom);
    }

    @Override
    public void remove(Classroom classroom) {
        classroomRepository.delete(classroom);
    }

    @Override
    public void remove(List<Classroom> classrooms) {
        classroomRepository.delete(classrooms);
    }

    @Override
    public List<Classroom> getAll() {
        return Lists.newArrayList(classroomRepository.findAll());
    }

    @Override
    public Page<Classroom> getPage(Pageable pageable) {
        return classroomRepository.findAll(pageable);
    }

    @Override
    public Classroom getWithId(Long id) {
        return classroomRepository.findOne(id);
    }
}
