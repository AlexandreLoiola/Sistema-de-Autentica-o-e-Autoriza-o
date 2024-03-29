package com.AlexandreLoiola.AccessManagement.service.exceptions.authorization;

import jakarta.persistence.EntityNotFoundException;

public class AuthorizationNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public AuthorizationNotFoundException(String msg) {
        super(msg);
    }

    public AuthorizationNotFoundException(String msg, Throwable cause) {
        super(msg);
        this.initCause(cause);
    }
}
