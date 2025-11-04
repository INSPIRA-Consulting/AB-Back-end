package com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject;

import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject.UnidadeMedidaEnum;

import java.util.List;

public enum FormaPagamentoEnum {

    DINHEIRO("Dinheiro"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    VOUCHER("Vale Alimentação/Refeição"),
    PIX("PIX");

    private final String formaPagamento;

    FormaPagamentoEnum(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public static boolean contains(String formaPagamento) {
        for (FormaPagamentoEnum f : FormaPagamentoEnum.values()) {
            if (f.getFormaPagamento().equalsIgnoreCase(formaPagamento) ||
                    f.name().equalsIgnoreCase(formaPagamento)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> names() {
        return List.of(
                DINHEIRO.formaPagamento,
                CARTAO_CREDITO.formaPagamento,
                CARTAO_DEBITO.formaPagamento,
                VOUCHER.formaPagamento,
                PIX.formaPagamento
        );
    }

    public static FormaPagamentoEnum from(String formaPagamento) {
        if (formaPagamento == null) {
            throw new InvalidArgumentException("Forma de Pagamento não pode ser nula.");
        }
        for (FormaPagamentoEnum f : FormaPagamentoEnum.values()) {
            if (f.name().equalsIgnoreCase(formaPagamento) || f.getFormaPagamento().equalsIgnoreCase(formaPagamento)) {
                return f;
            }
        }
        throw new InvalidArgumentException("Forma de Pagamento Inválida: " + formaPagamento + ". Formas de Pagamento válidas: " + names());
    }

}