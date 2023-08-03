package com.lql.hellospringsecurity.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MailServiceTest {




    @ParameterizedTest
    @ValueSource(strings = {"lequilong777@gmail.com", "nam@gmail.com", "hieunguyenz@gmail.vn", "numkhuc234@gmail.com"})
    void isEmailValid(String email) {
         Pattern emailPattern =  Pattern.compile("^[\\w\\d]{8,30}@gmail.com$");

         assertTrue(emailPattern.matcher(email).matches());
    }
}