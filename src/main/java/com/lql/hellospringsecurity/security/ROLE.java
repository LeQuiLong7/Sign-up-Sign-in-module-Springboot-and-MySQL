package com.lql.hellospringsecurity.security;

import com.lql.hellospringsecurity.auth.Authority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.lql.hellospringsecurity.security.PERMISSION.*;

public enum ROLE {
    CLIENT(Set.of(WRITE, READ)),
    USER(Set.of(READ)),
    ADMIN(Set.of(DELETE, UPDATE, READ, WRITE));


    private final Set<PERMISSION> permissions;

    ROLE(Set<PERMISSION> permissions) {
        this.permissions = permissions;
    }

    public Set<PERMISSION> getPermissions() {
        return permissions;
    }

    public Set<Authority> getGrantedAuthorities() {
        Set<Authority> grantedAuthorities = permissions.stream()
                .map(permission -> new Authority(permission.name()))
                .collect(Collectors.toSet());

        grantedAuthorities.add(new Authority("ROLE_" + this.name()));

        return grantedAuthorities;
    }
}
