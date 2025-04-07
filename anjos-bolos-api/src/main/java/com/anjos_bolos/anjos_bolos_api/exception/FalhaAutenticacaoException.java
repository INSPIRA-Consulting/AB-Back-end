package com.anjos_bolos.anjos_bolos_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class FalhaAutenticacaoException extends RuntimeException {
    public FalhaAutenticacaoException(String message) {
        super(message);
    }
}
