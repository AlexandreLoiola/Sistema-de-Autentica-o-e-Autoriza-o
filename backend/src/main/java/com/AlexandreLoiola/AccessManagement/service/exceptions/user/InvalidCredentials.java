package com.AlexandreLoiola.AccessManagement.service.exceptions.user;

import org.springframework.security.core.AuthenticationException;

public class InvalidCredentials  extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    public InvalidCredentials(String msg) { super(msg); }

    public InvalidCredentials(String msg, Throwable cause) {super(msg, cause);}
}

