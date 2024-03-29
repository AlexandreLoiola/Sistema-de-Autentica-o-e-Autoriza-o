package com.AlexandreLoiola.AccessManagement.rest.controler.exceptions;


import com.AlexandreLoiola.AccessManagement.service.exceptions.authorization.AuthorizationNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleUpdateException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionsDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        String errorMsg = ex.getMessage();
        ExceptionsDto exceptionsDto = new ExceptionsDto(
                System.currentTimeMillis(),
                HttpStatus.CONFLICT.value(),
                "Data Integrity Violation",
                errorMsg,
                request.getRequestURI());
        return new ResponseEntity<>(exceptionsDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionsDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMsg = ex.getBindingResult().getFieldError().getDefaultMessage();
        ExceptionsDto exceptionsDto = new ExceptionsDto(
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Request",
                errorMsg,
                request.getRequestURI());
        return new ResponseEntity<>(exceptionsDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionsDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String errorMsg = ex.getMessage();
        ExceptionsDto exceptionsDto = new ExceptionsDto(
                System.currentTimeMillis(),
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Request",
                errorMsg,
                request.getRequestURI());
        return new ResponseEntity<>(exceptionsDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionsDto> handleHttpMessageNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        String errorMsg = ex.getMessage();
        ExceptionsDto exceptionsDto = new ExceptionsDto(
                System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                errorMsg,
                request.getRequestURI());
        return new ResponseEntity<>(exceptionsDto, HttpStatus.NOT_FOUND);
    }
}