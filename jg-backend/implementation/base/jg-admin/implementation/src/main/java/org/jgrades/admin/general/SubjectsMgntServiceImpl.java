package org.jgrades.admin.general;

import org.jgrades.admin.api.general.SubjectsMgntService;
import org.jgrades.admin.common.AbstractPagingMgntService;
import org.jgrades.data.api.dao.SubjectRepository;
import org.jgrades.data.api.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectsMgntServiceImpl extends AbstractPagingMgntService<Subject, Long, SubjectRepository> implements SubjectsMgntService {
    @Autowired
    public SubjectsMgntServiceImpl(SubjectRepository repository) {
        super(repository);
    }
}
