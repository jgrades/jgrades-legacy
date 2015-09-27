package org.jgrades.admin.accounts;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.data.api.dao.AbstaractUserRepository;
import org.jgrades.data.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class AbstractUserMgntServiceImpl<U extends User, R extends AbstaractUserRepository<U>> implements UserMgntService<U> {
    private final R repository;

    @Autowired
    public AbstractUserMgntServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public void saveOrUpdate(U user) {
        repository.save(user);
    }

    @Override
    public void remove(U user) {
        repository.delete(user);
    }

    @Override
    public void remove(List<U> users) {
        repository.delete(users);
    }

    @Override
    public Page<U> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<U> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public U getWithId(Long id) {
        return repository.findOne(id);
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
