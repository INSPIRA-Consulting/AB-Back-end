package com.anjos_bolos.anjos_bolos_api.core.application.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}