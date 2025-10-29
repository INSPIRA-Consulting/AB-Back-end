package com.anjos_bolos.anjos_bolos_api.core.application.exception;

public class UnauthorizedAcessException extends RuntimeException {
    public UnauthorizedAcessException(String message) {
        super(message);
    }
}
