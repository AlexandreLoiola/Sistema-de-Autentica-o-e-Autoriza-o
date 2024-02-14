package com.AlexandreLoiola.AccessManagement.service.exceptions.user;

public class UserUpdateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserUpdateException(String msg) { super(msg); }

    public UserUpdateException(String msg, Throwable cause) {super(msg, cause);}
}

