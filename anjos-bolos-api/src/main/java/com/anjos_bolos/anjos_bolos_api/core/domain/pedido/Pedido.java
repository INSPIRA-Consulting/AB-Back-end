package com.anjos_bolos.anjos_bolos_api.core.domain.pedido;

import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject.StatusPedidoEnum;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;
import org.springframework.format.annotation.DateTimeFormat;

public class Pedido {
    private Integer id;
    private DateTimeFormat dataPedido;
    private DateTimeFormat dataEntrega;
    private DateTimeFormat dataPagamento;
    private StatusPedidoEnum status;
    private String observacao;
    private Usuario atendente;
    private Cliente cliente;
}
