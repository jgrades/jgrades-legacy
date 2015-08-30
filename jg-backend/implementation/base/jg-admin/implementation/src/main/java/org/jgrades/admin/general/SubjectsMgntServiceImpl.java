package org.jgrades.admin.general;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.general.SubjectsMgntService;
import org.jgrades.data.api.dao.SubjectRepository;
import org.jgrades.data.api.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectsMgntServiceImpl implements SubjectsMgntService {
    @Autowired
    private SubjectRepository repository;

    @Override
    public void saveOrUpdate(Subject subject) {
        repository.save(subject);
    }

    @Override
    public void remove(Subject subject) {
        repository.delete(subject);
    }

    @Override
    public void remove(List<Subject> subjects) {
        repository.delete(subjects);
    }

    @Override
    public List<Subject> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Page<Subject> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Subject getWithId(Long id) {
        return repository.findOne(id);
    }
}
