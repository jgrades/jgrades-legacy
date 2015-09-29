package org.jgrades.admin.structures;

import org.jgrades.admin.api.structures.SubGroupMgntService;
import org.jgrades.admin.common.AbstractPagingMgntService;
import org.jgrades.data.api.dao.structures.SubGroupRepository;
import org.jgrades.data.api.entities.SubGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubGroupMgntServiceImpl extends AbstractPagingMgntService<SubGroup, Long, SubGroupRepository> implements SubGroupMgntService {
    @Autowired
    public SubGroupMgntServiceImpl(SubGroupRepository repository) {
        super(repository);
    }
}
