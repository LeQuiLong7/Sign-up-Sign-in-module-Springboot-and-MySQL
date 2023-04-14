package com.lql.hellospringsecurity.exception.model;

public class EmailNotValidException extends RuntimeException {
    public EmailNotValidException(String email) {
        super(email + "is not a valid email");
    }
}
