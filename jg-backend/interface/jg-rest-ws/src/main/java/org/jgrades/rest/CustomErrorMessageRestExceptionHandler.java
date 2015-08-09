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
