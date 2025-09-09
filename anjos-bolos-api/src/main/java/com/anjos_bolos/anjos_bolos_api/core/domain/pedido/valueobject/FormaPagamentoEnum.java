package com.anjos_bolos.anjos_bolos_api.core.domain.pedido.valueobject;

public enum FormaPagamentoEnum {
    DINHEIRO("Dinheiro"),
    CARTAO_CREDITO("Cartão de Crédito"),
    CARTAO_DEBITO("Cartão de Débito"),
    VALE("Vale Alimentação/Refeição"),
    PIX("PIX");

    private final String formaPagamento;

    FormaPagamentoEnum(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
