package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoRespoonseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ProdutosPedidoResponseDTO(
        Integer pedidoId,
        LocalDateTime dataRetirada,
        List<ProdutoRespoonseDTO> produtos
) {}
