package com.lql.hellospringsecurity.auth;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false, unique = true)
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_id")
    )
    private Set<Authority> authorities;

    public CustomUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean addAuthority(Authority authority) {
        return authorities.add(authority);
    }
}
