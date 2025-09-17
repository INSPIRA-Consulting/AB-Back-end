package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario;

import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.FuncaoUsuarioEnum;

public record UsuarioResponseDTO(
        Integer id,
        String nome,
        CPF cpf,
        Email email,
        String senha,
        Telefone telefone,
        FuncaoUsuarioEnum funcao
) {}
