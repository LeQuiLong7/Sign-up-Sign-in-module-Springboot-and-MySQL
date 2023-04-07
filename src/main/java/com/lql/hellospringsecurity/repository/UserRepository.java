package com.lql.hellospringsecurity.repository;

import com.lql.hellospringsecurity.auth.CustomUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {


    Optional<CustomUser> findByUsername(String username);



}
