package com.AlexandreLoiola.AccessManagement.service.exceptions.method;

public class MethodInsertException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MethodInsertException(String msg) { super(msg); }

    public MethodInsertException(String msg, Throwable cause) {super(msg, cause);}
}

