package org.jgrades.security.api.dao;

import org.jgrades.data.api.entities.User;
import org.jgrades.security.api.entities.PasswordData;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordDataRepository {
    void setPasswordForUser(String password, User user);

    PasswordData getPasswordDataWithUser(User user);
}
