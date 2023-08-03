package com.lql.hellospringsecurity.repository;

import com.lql.hellospringsecurity.auth.CustomUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    public void userRepository_Save_ReturnUserWithNewID() {

        CustomUser user = new CustomUser("test", "test");
        user.setId(435);

        CustomUser savedUser = userRepository.saveAndFlush(user);

        assertTrue(savedUser.getId() > 0);
    }





}