package com.anjos_bolos.anjos_bolos_api.core.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CadastroConflitoException extends RuntimeException {
    public CadastroConflitoException(String message) {
        super(message);
    }
}

