package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.exception.DuplicateKeyException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.EntityAlreadyExistsException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.application.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler (InvalidArgumentException.class)
    public ResponseEntity<String> handleInvalidArgument(InvalidArgumentException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler (NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler (EntityAlreadyExistsException.class)
    public ResponseEntity<String> handleConflict(EntityAlreadyExistsException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler (DuplicateKeyException.class)
    public ResponseEntity<String> handleDuplicateKey(DuplicateKeyException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

}