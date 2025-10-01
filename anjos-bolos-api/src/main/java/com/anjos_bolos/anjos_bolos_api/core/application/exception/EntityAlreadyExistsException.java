package com.anjos_bolos.anjos_bolos_api.core.application.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

}