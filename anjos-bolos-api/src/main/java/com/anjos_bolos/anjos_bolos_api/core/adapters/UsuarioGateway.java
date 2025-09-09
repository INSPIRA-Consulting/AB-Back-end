package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

import java.util.List;

public interface UsuarioGateway {

    Usuario save(Usuario usuario);

    boolean existsByCpf(String cpf);

    List<Usuario> findAll();

    Usuario findById(Integer id);

    Usuario findByCpf(String cpf);

    List<Usuario> findByNome(String nome);

    void update(Usuario usuario);

    void delete(Integer id);
}
