package com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject;

import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;

import java.util.regex.Pattern;

public class Telefone {

    private final String value;
    private static Pattern pattern = Pattern.compile("^\\(?\\d{2}\\)?\\s?(?:9\\d{4}|\\d{4})-?\\d{4}$");

    public Telefone(String value) {
        this.value = value;
    }

    public static Telefone of(String value) {
        if (value == null || !pattern.matcher(value).matches()) {
            throw new InvalidArgumentException("Telefone deve estar no formato '(XX) 9XXXX-XXXX'.");
        }
        return new Telefone(value);
    }

    @Override
    public String toString() {
        return value;
    }

}