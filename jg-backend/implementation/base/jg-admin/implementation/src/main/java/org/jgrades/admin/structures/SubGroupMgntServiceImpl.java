package org.jgrades.admin.structures;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.structures.SubGroupMgntService;
import org.jgrades.data.api.dao.structures.SubGroupRepository;
import org.jgrades.data.api.entities.SubGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubGroupMgntServiceImpl implements SubGroupMgntService {
    @Autowired
    private SubGroupRepository repository;

    @Override
    public void saveOrUpdate(SubGroup subGroup) {
        repository.save(subGroup);
    }

    @Override
    public void remove(SubGroup subGroup) {
        repository.delete(subGroup);
    }

    @Override
    public void remove(List<SubGroup> subGroups) {
        repository.delete(subGroups);
    }

    @Override
    public Page<SubGroup> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<SubGroup> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public SubGroup getWithId(Long id) {
        return repository.findOne(id);
    }
}
