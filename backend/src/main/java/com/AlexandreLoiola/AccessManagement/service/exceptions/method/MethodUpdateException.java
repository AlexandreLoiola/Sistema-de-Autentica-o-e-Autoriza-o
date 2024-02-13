package com.AlexandreLoiola.AccessManagement.service.exceptions.method;

public class MethodUpdateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MethodUpdateException(String msg) { super(msg); }

    public MethodUpdateException(String msg, Throwable cause) {super(msg, cause);}
}

