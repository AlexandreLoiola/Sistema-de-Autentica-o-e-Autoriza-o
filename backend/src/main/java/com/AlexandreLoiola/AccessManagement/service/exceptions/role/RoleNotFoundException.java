package com.AlexandreLoiola.AccessManagement.service.exceptions.role;

import jakarta.persistence.EntityNotFoundException;

public class RoleNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public RoleNotFoundException(String msg) { super(msg); }

    public RoleNotFoundException(String msg, Throwable cause) {
        super(msg);
        this.initCause(cause);
    }
}
