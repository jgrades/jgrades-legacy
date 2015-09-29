package org.jgrades.admin.common;

import org.jgrades.admin.api.common.CrudPagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public abstract class AbstractPagingMgntService<T, ID extends Serializable, R extends PagingAndSortingRepository<T, ID>> extends AbstractMgntService<T, ID, R> implements CrudPagingService<T, ID> {
    @Autowired
    public AbstractPagingMgntService(R repository) {
        super(repository);
    }

    @Override
    public Page<T> getPage(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
