package org.jgrades.admin.general;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.general.SubjectsMgntService;
import org.jgrades.data.api.entities.Subject;
import org.jgrades.data.dao.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectsMgntServiceImpl implements SubjectsMgntService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public void saveOrUpdate(Subject subject) {
        subjectRepository.save(subject);
    }

    @Override
    public void remove(Subject subject) {
        subjectRepository.delete(subject);
    }

    @Override
    public void remove(List<Subject> subjects) {
        subjectRepository.delete(subjects);
    }

    @Override
    public List<Subject> getAll() {
        return Lists.newArrayList(subjectRepository.findAll());
    }

    @Override
    public Page<Subject> getPage(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    @Override
    public Subject getWithId(Long id) {
        return subjectRepository.findOne(id);
    }
}
