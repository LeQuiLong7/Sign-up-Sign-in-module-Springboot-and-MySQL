package com.lql.hellospringsecurity.bean;

import com.lql.hellospringsecurity.auth.Authority;
import com.lql.hellospringsecurity.repository.AuthorityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeanConfigTest {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    void runner() {
        new Authority("test");


    }
}