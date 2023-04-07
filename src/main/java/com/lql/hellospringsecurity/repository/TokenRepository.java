package com.lql.hellospringsecurity.repository;

import com.lql.hellospringsecurity.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
