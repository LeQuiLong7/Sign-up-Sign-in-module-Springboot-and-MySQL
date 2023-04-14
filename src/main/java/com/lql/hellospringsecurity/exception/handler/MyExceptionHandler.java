package com.lql.hellospringsecurity.exception.handler;

import com.lql.hellospringsecurity.exception.model.EmailNotValidException;
import com.lql.hellospringsecurity.exception.model.MyUsernameNotFoundException;
import com.lql.hellospringsecurity.exception.model.RoleNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MyUsernameNotFoundException.class)
    public ResponseEntity<String> userNotFoundException() {
        return ResponseEntity.badRequest().body("user not found");
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> roleNotFoundException() {
        return ResponseEntity.badRequest().body("role not found");
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> jwtExpired() {
        return new ResponseEntity<>("jwt-expired", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDenied() {
        return new ResponseEntity<>("access-denied", HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception exception) {
        return new ResponseEntity<>(exception.getClass(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException exception) {
        return new ResponseEntity<>(exception.getClass(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<?> emailNotValidException(EmailNotValidException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
