package org.jgrades.admin.accounts;

import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.admin.common.AbstractPagingMgntService;
import org.jgrades.data.api.dao.AbstaractUserRepository;
import org.jgrades.data.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class AbstractUserMgntServiceImpl<U extends User, R extends AbstaractUserRepository<U>> extends AbstractPagingMgntService<U, Long, R> implements UserMgntService<U> {
    @Autowired
    public AbstractUserMgntServiceImpl(R repository) {
        super(repository);
    }

    @Override
    public List<U> get(Specification<U> specification) {
        return repository.findAll(specification);
    }

    @Override
    public Page<U> getPage(Pageable pageable, Specification<U> specification) {
        return repository.findAll(specification, pageable);
    }
}
