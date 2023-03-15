package com.lql.hellospringsecurity.exception.model;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RoleNotFoundException extends UsernameNotFoundException {

    public RoleNotFoundException() {
        super("Role not found");
    }
}
