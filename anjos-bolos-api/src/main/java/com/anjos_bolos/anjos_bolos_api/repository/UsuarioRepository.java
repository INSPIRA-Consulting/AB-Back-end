package com.anjos_bolos.anjos_bolos_api.repository;

import com.anjos_bolos.anjos_bolos_api.dto.UsuarioLoginDto;
import com.anjos_bolos.anjos_bolos_api.entity.Funcao;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    Optional<Usuario> findByNome(String nome);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByNomeIgnoreCase(String nome);

    boolean existsByCpf(String cpf);

    List<Usuario> findByFuncao(Funcao funcao);

    Optional<UsuarioLoginDto> findByEmail(String email);
}
