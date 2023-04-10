package com.lql.hellospringsecurity.model;


import com.lql.hellospringsecurity.auth.Authority;

import java.util.Set;

public record UserDTO (long id, String username, Set<Authority> authorities ) {
}
