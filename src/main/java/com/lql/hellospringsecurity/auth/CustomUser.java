package com.lql.hellospringsecurity.auth;


import com.lql.hellospringsecurity.model.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Set;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class CustomUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false, unique = true)
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_id")
    )
    private Set<Authority> authorities ;

    @Column(columnDefinition = "BIT default 0")
    private boolean isActive;


    public CustomUser(String username, String password, boolean isActive) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }
    public CustomUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.isActive = false;
    }


    public boolean addAuthority(Authority... authority) {
        return authorities.addAll(Arrays.asList(authority));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDTO mapToUserDTO(CustomUser user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getAuthorities());
    }
}
