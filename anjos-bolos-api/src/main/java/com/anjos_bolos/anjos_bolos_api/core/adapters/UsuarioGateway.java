package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.FuncaoUsuarioEnum;

import java.util.List;

public interface UsuarioGateway {

    Usuario save(Usuario usuario);

    boolean existsById(Integer id);

    boolean existsByCpf(CPF cpf);

    boolean existsByEmail(Email email);

    boolean existsByTelefone(Telefone telefone);

    boolean existsByCpfAndIdNot(CPF cpf, Integer id);

    boolean existsByEmailAndIdNot(Email email, Integer id);

    boolean existsByTelefoneAndIdNot(Telefone telefone, Integer id);

    List<Usuario> findAll();

    Usuario findById(Integer id);

    List<Usuario> findByNome(String nome);

    Usuario findByCpf(CPF cpf);

    Usuario findByEmail(Email email);

    List<Usuario> findByFuncao(FuncaoUsuarioEnum funcao);

    Usuario update(Usuario usuario);

    void delete(Integer id);
}
