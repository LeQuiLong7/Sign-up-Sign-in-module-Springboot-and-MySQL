package com.lql.hellospringsecurity.exception.model;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUsernameNotFoundException extends UsernameNotFoundException {

    public MyUsernameNotFoundException() {
        super(":(");
    }
}
