package com.AlexandreLoiola.AccessManagement.service.exceptions.authorization;

public class AuthorizationNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthorizationNotFoundException(String msg) { super(msg); }

    public AuthorizationNotFoundException(String msg, Throwable cause) {super(msg, cause);}
}
