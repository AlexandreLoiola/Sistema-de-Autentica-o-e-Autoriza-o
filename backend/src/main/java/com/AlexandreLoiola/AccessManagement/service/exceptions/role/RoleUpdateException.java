package com.AlexandreLoiola.AccessManagement.service.exceptions.role;

import org.springframework.dao.DataIntegrityViolationException;

public class RoleUpdateException extends DataIntegrityViolationException {
    private static final long serialVersionUID = 1L;

    public RoleUpdateException(String msg) { super(msg); }

    public RoleUpdateException(String msg, Throwable cause) {super(msg, cause);}
}

