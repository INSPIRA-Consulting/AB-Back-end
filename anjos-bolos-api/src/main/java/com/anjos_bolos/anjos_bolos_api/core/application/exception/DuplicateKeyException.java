package com.anjos_bolos.anjos_bolos_api.core.application.exception;

public class DuplicateKeyException extends RuntimeException
{
    public DuplicateKeyException(String message) {
        super(message);
    }

}