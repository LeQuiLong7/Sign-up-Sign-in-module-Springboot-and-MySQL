package com.lql.hellospringsecurity.exception.model;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException() {

    }
    public StudentNotFoundException(String message) {
        super(message);
    }

}
