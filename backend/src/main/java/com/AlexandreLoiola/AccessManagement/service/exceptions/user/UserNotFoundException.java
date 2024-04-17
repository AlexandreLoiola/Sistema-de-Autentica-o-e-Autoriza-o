package com.AlexandreLoiola.AccessManagement.service.exceptions.user;


import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException  extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String msg) { super(msg); }

    public UserNotFoundException(String msg, Throwable cause) {
        super(msg);
        this.initCause(cause);
    }
}
