package com.lql.hellospringsecurity.exception.model;

public class ConfirmationTokenExpiredException extends RuntimeException{
    public ConfirmationTokenExpiredException() {
        super("Confirmation token is already expired!");
    }
}
