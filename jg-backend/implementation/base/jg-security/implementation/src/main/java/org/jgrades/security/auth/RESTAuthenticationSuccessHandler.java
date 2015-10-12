/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.data.api.entities.User;
import org.jgrades.security.api.dao.PasswordDataRepository;
import org.jgrades.security.api.model.LoginResult;
import org.jgrades.security.service.LockingManager;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserMgntService userMgntService;

    @Autowired
    private PasswordDataRepository passwordDataRepository;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private LockingManager lockingManager;

    public RESTAuthenticationSuccessHandler() {
        super();
        setRedirectStrategy(new NoRedirectStrategy());
    }

    @Override
    @Transactional("mainTransactionManager")
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userMgntService.getWithLogin(userDetails.getUsername());
        user.setLastVisit(DateTime.now());
        response.getWriter().write(jacksonObjectMapper.writeValueAsString(new LoginResult()));
        lockingManager.removeLockIfPossible(user);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
