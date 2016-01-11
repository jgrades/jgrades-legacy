/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest;

import cz.jirutka.spring.exhandler.handlers.ErrorMessageRestExceptionHandler;
import cz.jirutka.spring.exhandler.messages.ErrorMessage;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public class CustomErrorMessageRestExceptionHandler<E extends Exception> extends ErrorMessageRestExceptionHandler<E> {
    public CustomErrorMessageRestExceptionHandler(Class<E> exceptionClass, HttpStatus status) {
        super(exceptionClass, status);
    }

    protected CustomErrorMessageRestExceptionHandler(HttpStatus status) {
        super(status);
    }

    @Override
    public ErrorMessage createBody(E ex, HttpServletRequest req) {
        ErrorMessage m = new ErrorMessage();
        m.setType(URI.create(resolveMessage(TYPE_KEY, ex, req)));
        m.setTitle(ex.getClass().getSimpleName());
        m.setStatus(getStatus());
        m.setDetail(ex.getMessage());
        return m;
    }


}
