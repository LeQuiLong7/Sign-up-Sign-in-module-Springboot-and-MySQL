package com.lql.hellospringsecurity.repository;

import com.lql.hellospringsecurity.auth.CustomUser;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

    @Cacheable("users")
    Page<CustomUser> findAll(Pageable pageable);

    @Cacheable("users")
    Optional<CustomUser> findByUsername(String username);

    CustomUser getByUsername(String username);

    boolean existsByUsername(String username);



}
