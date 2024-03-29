package com.AlexandreLoiola.AccessManagement.service.exceptions.method;

import org.springframework.dao.DataIntegrityViolationException;

public class MethodInsertException extends DataIntegrityViolationException {
    private static final long serialVersionUID = 1L;

    public MethodInsertException(String msg) { super(msg); }

    public MethodInsertException(String msg, Throwable cause) {super(msg, cause);}
}

