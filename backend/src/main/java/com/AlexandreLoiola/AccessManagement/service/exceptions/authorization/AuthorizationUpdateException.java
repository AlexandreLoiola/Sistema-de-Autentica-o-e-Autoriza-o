package com.AlexandreLoiola.AccessManagement.service.exceptions.authorization;

import org.springframework.dao.DataIntegrityViolationException;

public class AuthorizationUpdateException extends DataIntegrityViolationException {
    private static final long serialVersionUID = 1L;

    public AuthorizationUpdateException(String msg) { super(msg); }

    public AuthorizationUpdateException(String msg, Throwable cause) {super(msg, cause);}
}

