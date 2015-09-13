package org.jgrades.security.dao;

import org.jgrades.data.api.entities.User;
import org.jgrades.security.api.dao.InternalPasswordDataRepository;
import org.jgrades.security.api.dao.PasswordDataRepository;
import org.jgrades.security.api.entities.PasswordData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("passwordDataRepository")//TODO
public class PasswordDataRepositoryImpl implements PasswordDataRepository {
    @Autowired
    @Qualifier("internalPasswordDataRepository")
    private InternalPasswordDataRepository internalPasswordDataRepository;

    @Override
    public void setPasswordForUser(String password, User user) {

    }

    @Override
    public PasswordData getPasswordDataWithUser(User user) {
        return null;
    }
}
