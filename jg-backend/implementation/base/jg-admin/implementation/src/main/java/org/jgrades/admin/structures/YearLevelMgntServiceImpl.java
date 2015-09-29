package org.jgrades.admin.structures;

import org.jgrades.admin.api.structures.YearLevelMgntService;
import org.jgrades.admin.common.AbstractPagingMgntService;
import org.jgrades.data.api.dao.structures.YearLevelRepository;
import org.jgrades.data.api.entities.YearLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YearLevelMgntServiceImpl extends AbstractPagingMgntService<YearLevel, Long, YearLevelRepository> implements YearLevelMgntService {
    @Autowired
    public YearLevelMgntServiceImpl(YearLevelRepository repository) {
        super(repository);
    }
}
