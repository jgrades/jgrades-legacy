package org.jgrades.security.api.service;

import org.jgrades.data.api.entities.User;

public interface PasswordMgntService {
    void setPassword(String password, User user);

    String getPassword(User user);

    boolean isPasswordExpired(User user);

}
