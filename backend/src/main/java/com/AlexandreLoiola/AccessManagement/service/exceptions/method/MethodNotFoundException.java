package com.AlexandreLoiola.AccessManagement.service.exceptions.method;

import jakarta.persistence.EntityNotFoundException;

public class MethodNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public MethodNotFoundException(String msg) { super(msg); }

    public MethodNotFoundException(String msg, Throwable cause) {
        super(msg);
        this.initCause(cause);
    }
}
