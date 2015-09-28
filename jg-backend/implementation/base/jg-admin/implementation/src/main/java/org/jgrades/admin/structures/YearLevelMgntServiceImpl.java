package org.jgrades.admin.structures;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.structures.YearLevelMgntService;
import org.jgrades.data.api.dao.structures.YearLevelRepository;
import org.jgrades.data.api.entities.YearLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearLevelMgntServiceImpl implements YearLevelMgntService {
    @Autowired
    private YearLevelRepository repository;

    @Override
    public void saveOrUpdate(YearLevel yearLevel) {
        repository.save(yearLevel);
    }

    @Override
    public void remove(YearLevel yearLevel) {
        repository.delete(yearLevel);
    }

    @Override
    public void remove(List<YearLevel> yearLevels) {
        repository.delete(yearLevels);
    }

    @Override
    public Page<YearLevel> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<YearLevel> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public YearLevel getWithId(Long id) {
        return repository.findOne(id);
    }
}
