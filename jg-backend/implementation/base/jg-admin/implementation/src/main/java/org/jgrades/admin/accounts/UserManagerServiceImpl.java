package org.jgrades.admin.accounts;

import com.google.common.collect.Lists;
import org.jgrades.admin.api.accounts.UserManagerService;
import org.jgrades.data.api.dao.RoleUserRepository;
import org.jgrades.data.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagerServiceImpl<U extends User> implements UserManagerService<U> {
    @Autowired
    private RoleUserRepository<U> roleUserRepository;

    @Override
    public void saveOrUpdate(U user) {
        roleUserRepository.save(user);
    }

    @Override
    public void remove(U user) {
        roleUserRepository.delete(user);
    }

    @Override
    public void remove(List<U> users) {
        roleUserRepository.delete(users);
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
