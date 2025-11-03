package com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject;

import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;

import java.util.List;

public enum StatusPedidoEnum {

    CONFIRMADO("Confirmado"),
    PENDENTE_PAGAMENTO("Pendente de Pagamento"),
    CANCELADO("Cancelado"),
    FINALIZADO("Finalizado");

    private final String status;

    StatusPedidoEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static boolean contains(String status) {
        for (StatusPedidoEnum s : StatusPedidoEnum.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> names() {
        return List.of(
                CONFIRMADO.status,
                PENDENTE_PAGAMENTO.status,
                CANCELADO.status,
                FINALIZADO.status
        );
    }

    public static StatusPedidoEnum from(String status) {
        if (status == null) {
            throw new InvalidArgumentException("Status de Pedido pode ser nulo.");
        }
        for (StatusPedidoEnum s : StatusPedidoEnum.values()) {
            if (s.name().equalsIgnoreCase(status) || s.getStatus().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new InvalidArgumentException("Status de Pedido inválido: " + status + ". Status de Pedido válidos: " + names());
    }

}