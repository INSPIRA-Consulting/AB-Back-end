package com.anjos_bolos.anjos_bolos_api.repository;

import com.anjos_bolos.anjos_bolos_api.entity.Funcao;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    Optional<Usuario> findByNome(String nome);


    List<Usuario> findByFuncao(Funcao funcao);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndNome(String email, String nome);
}
