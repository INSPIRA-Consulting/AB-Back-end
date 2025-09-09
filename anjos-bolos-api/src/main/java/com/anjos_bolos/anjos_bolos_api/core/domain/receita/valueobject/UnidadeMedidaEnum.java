package com.anjos_bolos.anjos_bolos_api.core.domain.receita.valueobject;

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
}
