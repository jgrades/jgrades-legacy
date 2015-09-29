package org.jgrades.admin.structures;

import org.jgrades.admin.api.structures.DivisionMgntService;
import org.jgrades.admin.common.AbstractPagingMgntService;
import org.jgrades.data.api.dao.structures.DivisionRepository;
import org.jgrades.data.api.entities.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DivisionMgntServiceImpl extends AbstractPagingMgntService<Division, Long, DivisionRepository> implements DivisionMgntService {
    @Autowired
    public DivisionMgntServiceImpl(DivisionRepository repository) {
        super(repository);
    }
}
