package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.usuario.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.UsuarioUniquenessChecker;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.UsuarioValidator;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.UsuarioJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioBeanConfig {

    @Bean
    public UsuarioValidator usuarioValidator(UsuarioUniquenessChecker checker) {
        return new UsuarioValidator(checker);
    }

    @Bean
    CreateUsuarioUseCase createUsuarioUseCase(UsuarioJpaAdapter adapter, UsuarioValidator validator) {
        return new CreateUsuarioUseCase(adapter, validator);
    }

    @Bean
    UpdateUsuarioUseCase updateUsuarioUseCase(UsuarioJpaAdapter adapter, UsuarioValidator validator) {
        return new UpdateUsuarioUseCase(adapter, validator);
    }

    @Bean
    DeleteUsuarioUseCase deleteUsuarioUseCase(UsuarioJpaAdapter adapter) {
        return new DeleteUsuarioUseCase(adapter);
    }

    @Bean
    ListUsuariosUseCase listUsuariosUseCase(UsuarioJpaAdapter adapter) {
        return new ListUsuariosUseCase(adapter);
    }

    @Bean
    GetUsuarioByIdUseCase getUsuarioByIdUseCase(UsuarioJpaAdapter adapter) {
        return new GetUsuarioByIdUseCase(adapter);
    }

    @Bean
    GetUsuarioByCpfUseCase getUsuarioByCpfUseCase(UsuarioJpaAdapter adapter) {
        return new GetUsuarioByCpfUseCase(adapter);
    }

    @Bean
    GetUsuarioByEmailUseCase getUsuarioByEmailUseCase(UsuarioJpaAdapter adapter) {
        return new GetUsuarioByEmailUseCase(adapter);
    }

    @Bean
    ListUsuariosByNomeUseCase listUsuariosByNomeUseCase(UsuarioJpaAdapter adapter) {
        return new ListUsuariosByNomeUseCase(adapter);
    }

    @Bean
    ListUsuariosByFuncaoUseCase listUsuariosByFuncaoUseCase(UsuarioJpaAdapter adapter) {
        return new ListUsuariosByFuncaoUseCase(adapter);
    }

}