package com.gcrespo.demoparkapi.exception;

public class UserUniqueViolationException extends RuntimeException {

    public UserUniqueViolationException(String message) {
        super(message);
    }
}
