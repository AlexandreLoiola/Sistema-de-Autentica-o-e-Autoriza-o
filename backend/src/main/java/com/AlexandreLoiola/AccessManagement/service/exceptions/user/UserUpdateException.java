package com.AlexandreLoiola.AccessManagement.service.exceptions.user;

import org.springframework.dao.DataIntegrityViolationException;

public class UserUpdateException extends DataIntegrityViolationException {
    private static final long serialVersionUID = 1L;

    public UserUpdateException(String msg) { super(msg); }

    public UserUpdateException(String msg, Throwable cause) {super(msg, cause);}
}

