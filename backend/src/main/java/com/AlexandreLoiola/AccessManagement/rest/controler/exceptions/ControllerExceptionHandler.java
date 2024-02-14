package com.AlexandreLoiola.AccessManagement.rest.controler.exceptions;


import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleInsertException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleNotFoundException;
import com.AlexandreLoiola.AccessManagement.service.exceptions.role.RoleUpdateException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ControllerExceptionHandler {
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
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionsDto> handleRoleNotFoundException(RoleNotFoundException ex, HttpServletRequest request) {
        ExceptionsDto exceptionsDto = new ExceptionsDto(
                System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(exceptionsDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleInsertException.class)
    public ResponseEntity<ExceptionsDto> handleProductInsertException(RoleInsertException ex, HttpServletRequest request) {
        ExceptionsDto exceptionsDto = new ExceptionsDto(
                System.currentTimeMillis(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(exceptionsDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RoleUpdateException.class)
    public ResponseEntity<ExceptionsDto> handleProductUpdateException(RoleUpdateException ex, HttpServletRequest request) {
        ExceptionsDto exceptionsDto = new ExceptionsDto(
                System.currentTimeMillis(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(exceptionsDto, HttpStatus.INTERNAL_SERVER_ERROR);
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
}