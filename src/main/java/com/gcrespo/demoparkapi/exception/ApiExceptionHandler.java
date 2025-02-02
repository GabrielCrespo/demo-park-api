package com.gcrespo.demoparkapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                        HttpServletRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(
                request,
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Campo(s) Inválido(s)",
                e.getBindingResult());

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMessage);

    }

    @ExceptionHandler(UserUniqueViolationException.class)
    public ResponseEntity<ErrorMessage> uniqueViolationException(UserUniqueViolationException e,
                                                                     HttpServletRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(
                request,
                HttpStatus.CONFLICT,
                e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMessage);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException e,
                                                                 HttpServletRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(
                request,
                HttpStatus.NOT_FOUND,
                e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMessage);

    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException(PasswordInvalidException e,
                                                                 HttpServletRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(
                request,
                HttpStatus.BAD_REQUEST,
                e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMessage);

    }

}
