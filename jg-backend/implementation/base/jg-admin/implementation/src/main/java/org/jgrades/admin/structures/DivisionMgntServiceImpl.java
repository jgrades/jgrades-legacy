package org.jgrades.admin.structures;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.structures.DivisionMgntService;
import org.jgrades.data.api.dao.structures.DivisionRepository;
import org.jgrades.data.api.entities.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DivisionMgntServiceImpl implements DivisionMgntService {
    @Autowired
    private DivisionRepository repository;

    @Override
    public void saveOrUpdate(Division division) {
        repository.save(division);
    }

    @Override
    public void remove(Division division) {
        repository.delete(division);
    }

    @Override
    public void remove(List<Division> divisions) {
        repository.delete(divisions);
    }

    @Override
    public Page<Division> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Division> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Division getWithId(Long id) {
        return repository.findOne(id);
    }
}
