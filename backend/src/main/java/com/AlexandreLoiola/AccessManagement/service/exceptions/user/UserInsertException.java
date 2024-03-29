package com.AlexandreLoiola.AccessManagement.service.exceptions.user;

import org.springframework.dao.DataIntegrityViolationException;

public class UserInsertException extends DataIntegrityViolationException {
    private static final long serialVersionUID = 1L;

    public UserInsertException(String msg) { super(msg); }

    public UserInsertException(String msg, Throwable cause) {super(msg, cause);}
}

