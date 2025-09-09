package com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido;

import com.anjos_bolos.anjos_bolos_api.core.domain.itens_pedido.ItensPedido;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.entity.Receita;

public class DetalhamentoPedido {
    private Integer id;
    private ItensPedido itemPedido;
    private Receita receita;
    private String observacao;
}
