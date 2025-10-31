package com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject;

import java.util.List;

public enum FuncaoUsuarioEnum {

    ADMINISTRADOR("Administrador", 3, true, true),
    GERENTE("Gerente", 2, true, true),
    ATENDENTE("Atendente", 1, false, true);

    private String funcao;
    private Integer nivelAcesso;
    private boolean acessoCritico;
    private boolean ativo;

    FuncaoUsuarioEnum(String funcao, Integer nivelAcesso, boolean acessoCritico, boolean ativo) {
        this.funcao = funcao;
        this.nivelAcesso = nivelAcesso;
        this.acessoCritico = acessoCritico;
        this.ativo = ativo;
    }

    public String getFuncao() {
        return funcao;
    }

    public Integer getNivelAcesso() {
        return nivelAcesso;
    }

    public boolean isAcessoCritico() {
        return acessoCritico;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public static boolean contains(String funcao) {
        for (FuncaoUsuarioEnum f : FuncaoUsuarioEnum.values()) {
            if (f.getFuncao().equalsIgnoreCase(funcao)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> names() {
        return List.of(
                ADMINISTRADOR.name(),
                GERENTE.name(),
                ATENDENTE.name()
        );
    }
}