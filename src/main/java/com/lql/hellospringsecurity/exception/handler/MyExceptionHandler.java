package com.lql.hellospringsecurity.exception.handler;

import com.lql.hellospringsecurity.exception.model.CookieNotFoundException;
import com.lql.hellospringsecurity.exception.model.StudentNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler({StudentNotFoundException.class, CookieNotFoundException.class})
    public ResponseEntity<String> studentNotFoundException(Model model, StudentNotFoundException exception) {
        model.addAttribute("message", exception.getMessage());
        return ResponseEntity.badRequest().body("exception");
    }


}
