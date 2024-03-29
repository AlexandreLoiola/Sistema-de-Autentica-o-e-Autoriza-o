package com.AlexandreLoiola.AccessManagement.service.exceptions.authorization;

import org.springframework.dao.DataIntegrityViolationException;

public class AuthorizationInsertException extends DataIntegrityViolationException {
    private static final long serialVersionUID = 1L;

    public AuthorizationInsertException(String msg) { super(msg); }

    public AuthorizationInsertException(String msg, Throwable cause) {super(msg, cause);}
}

