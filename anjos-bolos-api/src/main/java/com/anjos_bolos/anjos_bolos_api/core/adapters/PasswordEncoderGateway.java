package com.anjos_bolos.anjos_bolos_api.core.adapters;

public interface PasswordEncoderGateway {

    String encode(String raw);

    boolean matches(String raw, String encoded);

}