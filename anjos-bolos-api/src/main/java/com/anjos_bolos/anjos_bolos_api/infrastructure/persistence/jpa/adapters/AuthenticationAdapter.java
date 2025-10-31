package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario.UsuarioDetalhesDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.UsuarioEntity;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.UsuarioJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationAdapter implements UserDetailsService {

    private final UsuarioJpaRepository repository;

    public AuthenticationAdapter(UsuarioJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity entity = repository.findByEmail(username);

        if (entity == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + username);
        }

        Usuario usuario = UsuarioEntityMapper.toDomain(entity);

        return new UsuarioDetalhesDTO(
                usuario.getNome(),
                usuario.getEmail().toString(),
                usuario.getSenha()
        );

        
    }
}