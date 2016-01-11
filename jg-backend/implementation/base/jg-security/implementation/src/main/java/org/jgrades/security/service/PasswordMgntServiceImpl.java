/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.service;

import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.data.api.entities.User;
import org.jgrades.security.api.dao.PasswordDataRepository;
import org.jgrades.security.api.entities.PasswordData;
import org.jgrades.security.api.service.PasswordMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PasswordMgntServiceImpl implements PasswordMgntService {
    @Autowired
    private PasswordDataRepository passwordDataRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserMgntService userMgntService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional("mainTransactionManager")
    public void setPassword(String password, User user) {
        User refreshedUser = userMgntService.getWithLogin(user.getLogin());
        PasswordData pswdData = passwordDataRepository.findOne(refreshedUser.getId());
        String encodedPassword = passwordEncoder.encode(password);
        if (pswdData == null) {
            pswdData = new PasswordData();
            pswdData.setId(refreshedUser.getId());
            pswdData.setUser(refreshedUser);
            pswdData.setPassword(encodedPassword);
            pswdData.setLastChange(LocalDateTime.now());
            passwordDataRepository.save(pswdData);
        } else {
            pswdData.setPassword(encodedPassword);
            pswdData.setLastChange(LocalDateTime.now());
        }
    }

    @Override
    public String getPassword(User user) {
        return passwordDataRepository.getPasswordDataWithUser(user.getLogin()).getPassword();
    }

    @Override
    public boolean isPasswordExpired(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
        return !userDetails.isCredentialsNonExpired();
    }
}
