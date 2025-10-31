package com.anjos_bolos.anjos_bolos_api.infrastructure.config.jwt;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.AuthenticationAdapter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthProvider implements AuthenticationProvider {

    private final AuthenticationAdapter adapter;

    private final PasswordEncoder passwordEncoder;

    public AuthProvider(AuthenticationAdapter adapter, PasswordEncoder passwordEncoder) {
        this.adapter = adapter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(final Authentication authentication)
            throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        UserDetails userDetails = adapter.loadUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        else {
            throw new BadCredentialsException("Usuário ou Senha Inválidos");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}