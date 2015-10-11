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
import org.jgrades.security.api.dao.PasswordDataRepository;
import org.jgrades.security.api.entities.PasswordData;
import org.jgrades.security.api.model.LoginResult;
import org.jgrades.security.service.LockingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RESTAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private PasswordDataRepository passwordDataRepository;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private LockingManager lockingManager;

    public RESTAuthenticationFailureHandler() {
        super();
        setRedirectStrategy(new NoRedirectStrategy());
    }

    @Override
    @Transactional("mainTransactionManager")
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String login = request.getParameter("username");
        PasswordData passwordData = passwordDataRepository.getPasswordDataWithUser(login);
        if (passwordData != null) {
            passwordData.increaseFailedLoginAmount();
        }
        lockingManager.setLockIfPossible(login);
        LoginResult loginResult = new LoginResult();
        loginResult.setSuccess(exception instanceof CredentialsExpiredException);
        loginResult.setErrorType(exception.getClass().getSimpleName());
        loginResult.setErrorMsg(exception.getMessage());
        response.getWriter().write(jacksonObjectMapper.writeValueAsString(loginResult));
    }
}
