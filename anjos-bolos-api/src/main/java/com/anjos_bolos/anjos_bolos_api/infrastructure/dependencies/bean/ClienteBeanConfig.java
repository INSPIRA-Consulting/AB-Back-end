package com.anjos_bolos.anjos_bolos_api.infrastructure.dependencies.bean;

import com.anjos_bolos.anjos_bolos_api.core.application.usecase.cliente.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.valueobject.ClienteUniquenessChecker;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.valueobject.ClienteValidator;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.UsuarioUniquenessChecker;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters.ClienteJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteBeanConfig {

    @Bean
    public ClienteValidator clienteValidator(ClienteUniquenessChecker checker) {
        return new ClienteValidator(checker);
    }

    @Bean
    public CreateClienteUseCase createClienteUseCase(ClienteJpaAdapter adapter, ClienteValidator validator) {
        return new CreateClienteUseCase(adapter, validator);
    }

    @Bean
    public UpdateClienteUseCase updateClienteUseCase(ClienteJpaAdapter adapter, ClienteValidator validator) {
        return new UpdateClienteUseCase(adapter, validator);
    }

    @Bean
    public DeleteClienteUseCase deleteClienteUseCase(ClienteJpaAdapter adapter) {
        return new DeleteClienteUseCase(adapter);
    }

    @Bean
    public ListClientesUseCase listClientesUseCase(ClienteJpaAdapter adapter) {
        return new ListClientesUseCase(adapter);
    }

    @Bean
    public GetClienteByIdUseCase getClienteByIdUseCase(ClienteJpaAdapter adapter) {
        return new GetClienteByIdUseCase(adapter);
    }

    @Bean
    public GetClienteByCpfUseCase getClienteByCpfUseCase(ClienteJpaAdapter adapter) {
        return new GetClienteByCpfUseCase(adapter);
    }

    @Bean
    public ListClientesByNomeUseCase listClientesByNomeUseCase(ClienteJpaAdapter adapter) {
        return new ListClientesByNomeUseCase(adapter);
    }

}