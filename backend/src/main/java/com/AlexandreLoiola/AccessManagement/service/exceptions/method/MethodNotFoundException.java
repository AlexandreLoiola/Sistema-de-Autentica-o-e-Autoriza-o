package com.AlexandreLoiola.AccessManagement.service.exceptions.method;

public class MethodNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MethodNotFoundException(String msg) { super(msg); }

    public MethodNotFoundException(String msg, Throwable cause) {super(msg, cause);}
}
