package com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject;

import java.util.regex.Pattern;

public class CPF {
    private final String value;
    private static Pattern pattern = Pattern.compile("^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$");

    public CPF(String value) {
        this.value = value;
    }

    public static CPF of(String value) {
        if (value == null || !pattern.matcher(value).matches()) {
            throw new IllegalArgumentException("CPF deve estar no formato 'XXX.XXX.XXX-XX'.");
        }
        return new CPF(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
