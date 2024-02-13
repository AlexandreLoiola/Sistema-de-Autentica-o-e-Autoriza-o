package com.AlexandreLoiola.AccessManagement.service.exceptions.authorization;

public class AuthorizationInsertException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthorizationInsertException(String msg) { super(msg); }

    public AuthorizationInsertException(String msg, Throwable cause) {super(msg, cause);}
}

