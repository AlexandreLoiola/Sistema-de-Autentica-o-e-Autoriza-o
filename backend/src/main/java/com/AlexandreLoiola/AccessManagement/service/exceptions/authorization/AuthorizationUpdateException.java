package com.AlexandreLoiola.AccessManagement.service.exceptions.authorization;

public class AuthorizationUpdateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthorizationUpdateException(String msg) { super(msg); }

    public AuthorizationUpdateException(String msg, Throwable cause) {super(msg, cause);}
}

