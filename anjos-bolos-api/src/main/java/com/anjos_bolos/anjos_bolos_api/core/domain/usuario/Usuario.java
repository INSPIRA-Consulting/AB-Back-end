package com.anjos_bolos.anjos_bolos_api.core.domain.usuario;

import com.anjos_bolos.anjos_bolos_api.core.domain.funcao_usuario.FuncaoUsuario;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;

public class Usuario {
    private Integer id;
    private CPF cpf;
    private Email email;
    private String senha;
    private Telefone telefone;
    private FuncaoUsuario funcao;



}
