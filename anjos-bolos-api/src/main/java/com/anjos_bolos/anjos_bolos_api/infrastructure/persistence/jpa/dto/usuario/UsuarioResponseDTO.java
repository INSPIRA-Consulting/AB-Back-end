package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.usuario;

import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.CPF;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Email;
import com.anjos_bolos.anjos_bolos_api.core.domain.shared.valueobject.Telefone;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject.FuncaoUsuarioEnum;

public record UsuarioResponseDTO(
        Integer id,
        String nome,
        String cpf,
        String email,
        String senha,
        String telefone,
        String funcao
) {}
