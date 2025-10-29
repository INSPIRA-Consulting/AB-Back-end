package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario.LoginUsuarioUseCase;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.UsuarioJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthBeanConfig {

    @Bean
    public LoginUsuarioUseCase loginUsuarioUseCase(UsuarioJpaAdapter adapter) {
        return new LoginUsuarioUseCase(adapter);
    }

}