package com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject;

import java.util.regex.Pattern;

public class Email {
    private final String value;
    private static Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Email(String value) {
        this.value = value;
    }

    public static Email of(String value) {
        if (value == null || !pattern.matcher(value).matches()) {
            throw new IllegalArgumentException("E-mail deve estar no formato 'email@domain.com'.");
        }
        return new Email(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
