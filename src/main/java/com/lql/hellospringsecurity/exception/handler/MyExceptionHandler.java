package com.lql.hellospringsecurity.exception.handler;

import com.lql.hellospringsecurity.exception.model.CookieNotFoundException;
import com.lql.hellospringsecurity.exception.model.MyUsernameNotFoundException;
import com.lql.hellospringsecurity.exception.model.RoleNotFoundException;
import com.lql.hellospringsecurity.exception.model.StudentNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MyUsernameNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(MyUsernameNotFoundException exception) {
        return ResponseEntity.badRequest().body("user not found");
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> roleNotFoundException(RoleNotFoundException exception) {
        return ResponseEntity.badRequest().body("role not found");
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> jwtExpired(ExpiredJwtException exception) {
        return new ResponseEntity<>("jwt-expired", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDenied(Exception exception) {
        return new ResponseEntity<>("access-denied", HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception exception) {
        return new ResponseEntity<>(exception.getClass(), HttpStatus.BAD_REQUEST);
    }


}
