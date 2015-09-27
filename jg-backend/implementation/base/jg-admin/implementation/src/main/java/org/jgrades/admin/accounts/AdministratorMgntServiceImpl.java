package org.jgrades.admin.accounts;

import org.jgrades.admin.api.accounts.AdministratorMgntService;
import org.jgrades.data.api.dao.accounts.AdministratorRepository;
import org.jgrades.data.api.entities.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorMgntServiceImpl extends AbstractUserMgntServiceImpl<Administrator, AdministratorRepository> implements AdministratorMgntService {
    @Autowired
    public AdministratorMgntServiceImpl(AdministratorRepository repository) {
        super(repository);
    }
}
