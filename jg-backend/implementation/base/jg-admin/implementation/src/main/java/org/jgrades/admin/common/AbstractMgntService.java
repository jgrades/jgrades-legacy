package org.jgrades.admin.common;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.common.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public abstract class AbstractMgntService<T, ID extends Serializable, R extends CrudRepository<T, ID>> implements CrudService<T, ID> {
    protected final R repository;

    @Autowired
    public AbstractMgntService(R repository) {
        this.repository = repository;
    }

    @Override
    public void saveOrUpdate(T obj) {
        repository.save(obj);
    }

    @Override
    public void remove(T obj) {
        repository.delete(obj);
    }

    @Override
    public void remove(List<T> objs) {
        repository.delete(objs);
    }

    @Override
    public List<T> getAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public T getWithId(ID id) {
        return repository.findOne(id);
    }
}
