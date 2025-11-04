package com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject;

import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;

import java.util.List;

public enum UnidadeMedidaEnum {

    UNIDADE("unidade", "unidades", "un", false),
    GRAMA("grama", "gramas", "g", true),
    QUILOGRAMA("quilograma", "quilogramas", "Kg", true),
    LITRO("litro", "litros", "L", true),
    MILILITRO("mililitro", "mililitros", "mL", true),
    XICARA_CHA("xícara(chá)", "xícaras(chá)", "xíc", true),
    COLHER_SOPA("colher(sopa)", "colheres(sopa)", "cs", true),
    COLHER_CHA("colher(chá)", "colheres(chá)", "cc", true),
    COLHER_CAFE("colher(café)", "colheres(café)", "ccf", true),
    COLHER_SOBREMESA("colher(sobremesa)", "colheres(sobremesa)", "csb", true),
    PITADA("pitada", "pitadas", "pt", false),
    COPO_AMERICANO("copo(americano)", "copos(americano)", "cp", true);

    private final String unidadeMedida;
    private final String plural;
    private final String simbolo;
    private final Boolean fracionavel;

    UnidadeMedidaEnum(String unidadeMedida, String plural, String simbolo, Boolean fracionavel) {
        this.unidadeMedida = unidadeMedida;
        this.plural = plural;
        this.simbolo = simbolo;
        this.fracionavel = fracionavel;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public String getPlural() {
        return plural;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public Boolean getFracionavel() {
        return fracionavel;
    }

    public static boolean contains(String unidadeMedida) {
        for (UnidadeMedidaEnum un : UnidadeMedidaEnum.values()) {
            if (un.getUnidadeMedida().equalsIgnoreCase(unidadeMedida)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> names() {
        return List.of(
                UNIDADE.name(),
                GRAMA.name(),
                QUILOGRAMA.name(),
                LITRO.name(),
                MILILITRO.name(),
                XICARA_CHA.name(),
                COLHER_SOPA.name(),
                COLHER_CHA.name(),
                COLHER_CAFE.name(),
                COLHER_SOBREMESA.name(),
                PITADA.name(),
                COPO_AMERICANO.name()
        );
    }

    public static UnidadeMedidaEnum from(String unidadeMedida) {
        if (unidadeMedida == null) {
            throw new InvalidArgumentException("Status de Pedido pode ser nulo.");
        }
        for (UnidadeMedidaEnum s : UnidadeMedidaEnum.values()) {
            if (s.name().equalsIgnoreCase(unidadeMedida) || s.getUnidadeMedida().equalsIgnoreCase(unidadeMedida)) {
                return s;
            }
        }
        throw new InvalidArgumentException("Status de Pedido inválido: " + unidadeMedida + ". Status de Pedido válidos: " + names());
    }

}