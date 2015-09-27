package org.jgrades.admin.accounts;

import org.jgrades.admin.api.accounts.ParentMgntService;
import org.jgrades.data.api.dao.accounts.ParentRepository;
import org.jgrades.data.api.entities.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentMgntServiceImpl extends AbstractUserMgntServiceImpl<Parent, ParentRepository> implements ParentMgntService {
    @Autowired
    public ParentMgntServiceImpl(ParentRepository repository) {
        super(repository);
    }
}
