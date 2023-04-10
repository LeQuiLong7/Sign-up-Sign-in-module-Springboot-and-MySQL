package com.lql.hellospringsecurity.repository;

import com.lql.hellospringsecurity.auth.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Optional<Authority> findAuthorityByAuthority(String authority);
    Authority getAuthorityByAuthority(String authority);
}
