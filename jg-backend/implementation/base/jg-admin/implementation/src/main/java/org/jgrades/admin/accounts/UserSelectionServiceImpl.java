package org.jgrades.admin.accounts;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.accounts.UserSelectionService;
import org.jgrades.data.api.dao.RoleUserRepository;
import org.jgrades.data.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSelectionServiceImpl<U extends User> implements UserSelectionService<U> {
    @Autowired
    private RoleUserRepository<U> roleUserRepository;

    @Override
    public List<U> get(Specification<U> specification) {
        return roleUserRepository.findAll(specification);
    }

    @Override
    public Page<U> getPsge(Pageable pageable, Specification<U> specification) {
        return roleUserRepository.findAll(specification, pageable);
    }

    @Override
    public Page<U> getPage(Pageable pageable) {
        return roleUserRepository.findAll(pageable);
    }

    @Override
    public List<U> getAll() {
        return Lists.newArrayList(roleUserRepository.findAll());
    }

    @Override
    public U getWithId(Long id) {
        return roleUserRepository.findOne(id);
    }
}
