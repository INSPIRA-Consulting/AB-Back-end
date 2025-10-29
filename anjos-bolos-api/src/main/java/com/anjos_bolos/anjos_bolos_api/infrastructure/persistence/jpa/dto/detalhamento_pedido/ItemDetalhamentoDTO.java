package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.detalhamento_pedido;

public record ItemDetalhamentoDTO(
        String nomeReceita,
        String tipoReceita,
        String observacao
) {}