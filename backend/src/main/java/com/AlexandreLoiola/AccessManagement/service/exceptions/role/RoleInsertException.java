package com.AlexandreLoiola.AccessManagement.service.exceptions.role;

import org.springframework.dao.DataIntegrityViolationException;

public class RoleInsertException  extends DataIntegrityViolationException {
    private static final long serialVersionUID = 1L;

    public RoleInsertException(String msg) { super(msg); }

    public RoleInsertException(String msg, Throwable cause) {super(msg, cause);}
}

