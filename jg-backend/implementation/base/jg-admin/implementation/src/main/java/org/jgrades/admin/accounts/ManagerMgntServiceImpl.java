package org.jgrades.admin.accounts;

import org.jgrades.admin.api.accounts.ManagerMgntService;
import org.jgrades.data.api.dao.accounts.ManagerRepository;
import org.jgrades.data.api.entities.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerMgntServiceImpl extends AbstractUserMgntServiceImpl<Manager, ManagerRepository> implements ManagerMgntService {
    @Autowired
    public ManagerMgntServiceImpl(ManagerRepository repository) {
        super(repository);
    }
}
