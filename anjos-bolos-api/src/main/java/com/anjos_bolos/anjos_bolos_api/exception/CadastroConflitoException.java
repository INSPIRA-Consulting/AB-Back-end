package com.anjos_bolos.anjos_bolos_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CadastroConflitoException extends Throwable {
    public CadastroConflitoException(String message) {
        super(message);
    }
}
