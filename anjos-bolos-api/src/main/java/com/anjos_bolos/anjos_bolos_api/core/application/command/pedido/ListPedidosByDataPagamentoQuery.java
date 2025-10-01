package com.anjos_bolos.anjos_bolos_api.core.application.command.pedido;

import java.time.LocalDateTime;

public record ListPedidosByDataPagamentoQuery(LocalDateTime dataPagamento) {}