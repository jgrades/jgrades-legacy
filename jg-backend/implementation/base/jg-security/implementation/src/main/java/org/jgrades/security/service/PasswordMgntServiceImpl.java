/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.service;

import org.jgrades.data.api.entities.User;
import org.jgrades.security.api.dao.PasswordDataRepository;
import org.jgrades.security.api.entities.PasswordData;
import org.jgrades.security.api.service.PasswordMgntService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordMgntServiceImpl implements PasswordMgntService {
    @Autowired
    private PasswordDataRepository passwordDataRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    @Transactional("mainTransactionManager")
    public void setPassword(String password, User user) {
        PasswordData pswdData = passwordDataRepository.findOne(user.getId());
        if (pswdData == null) {
            pswdData = new PasswordData();
            pswdData.setId(user.getId());
            pswdData.setUser(user);
            pswdData.setPassword(password);
            pswdData.setLastChange(DateTime.now());

            passwordDataRepository.save(pswdData);
        } else {
            pswdData.setLastChange(DateTime.now());
            passwordDataRepository.setPasswordForUser(password, user);
        }
    }

    @Override
    public String getPassword(User user) {
        return passwordDataRepository.getPasswordDataWithUser(user).getPassword();
    }

    @Override
    public boolean isPasswordExpired(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
        return !userDetails.isCredentialsNonExpired();
    }
}
