package org.jgrades.admin.general;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.general.ClassroomMgntService;
import org.jgrades.data.api.dao.ClassroomRepository;
import org.jgrades.data.api.entities.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomMgntServiceImpl implements ClassroomMgntService {
    @Autowired
    private ClassroomRepository repository;

    @Override
    public void saveOrUpdate(Classroom classroom) {
        repository.save(classroom);
    }

    @Override
    public void remove(Classroom classroom) {
        repository.delete(classroom);
    }

    @Override
    public void remove(List<Classroom> classrooms) {
        repository.delete(classrooms);
    }

    @Override
    public List<Classroom> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Page<Classroom> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Classroom getWithId(Long id) {
        return repository.findOne(id);
    }
}
