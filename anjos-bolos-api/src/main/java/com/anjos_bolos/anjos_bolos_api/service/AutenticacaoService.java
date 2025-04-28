package com.anjos_bolos.anjos_bolos_api.service;


import com.anjos_bolos.anjos_bolos_api.dto.usuario.UsuarioDetalhesDto;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;
import com.anjos_bolos.anjos_bolos_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
        Optional<Usuario> usuarioOpt = repository.findByEmail(username);

        if(usuarioOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("usuario: %s não encontrado", username));
        }
        return new UsuarioDetalhesDto(usuarioOpt.get());
    }
}
