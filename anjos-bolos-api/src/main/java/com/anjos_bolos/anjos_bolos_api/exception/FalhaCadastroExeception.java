package com.anjos_bolos.anjos_bolos_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FalhaCadastroException extends RuntimeException {
    public FalhaCadastroException(String message) {
        super(message);
    }
}

