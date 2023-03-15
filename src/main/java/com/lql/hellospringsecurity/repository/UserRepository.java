package com.lql.hellospringsecurity.repository;

import com.lql.hellospringsecurity.auth.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetail, String> {
}
