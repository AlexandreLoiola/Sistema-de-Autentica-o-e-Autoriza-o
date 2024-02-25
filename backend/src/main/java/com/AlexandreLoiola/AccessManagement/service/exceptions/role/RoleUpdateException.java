package com.AlexandreLoiola.AccessManagement.service.exceptions.role;

public class RoleUpdateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RoleUpdateException(String msg) { super(msg); }

    public RoleUpdateException(String msg, Throwable cause) {super(msg, cause);}
}

