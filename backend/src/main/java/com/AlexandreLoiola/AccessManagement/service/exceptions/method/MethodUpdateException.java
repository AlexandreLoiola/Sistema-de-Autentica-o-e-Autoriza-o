package com.AlexandreLoiola.AccessManagement.service.exceptions.method;

import org.springframework.dao.DataIntegrityViolationException;

public class MethodUpdateException extends DataIntegrityViolationException {
    private static final long serialVersionUID = 1L;

    public MethodUpdateException(String msg) { super(msg); }

    public MethodUpdateException(String msg, Throwable cause) {super(msg, cause);}
}

