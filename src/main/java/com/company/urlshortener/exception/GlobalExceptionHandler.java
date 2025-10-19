package com.company.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<?> handleUrlNotFound(UrlNotFoundException ex) {
        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "message", ex.getMessage()
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(Map.of(
                "timestamp", LocalDateTime.now(),
                "message", ex.getMessage()
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
