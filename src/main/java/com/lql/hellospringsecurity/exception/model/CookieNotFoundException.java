package com.lql.hellospringsecurity.exception.model;

public class CookieNotFoundException extends RuntimeException{
    public CookieNotFoundException() {
        super("Cookie not found :(");
    }
}
