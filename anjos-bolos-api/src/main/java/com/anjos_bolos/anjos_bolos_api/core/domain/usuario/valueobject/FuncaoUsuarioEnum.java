package com.anjos_bolos.anjos_bolos_api.core.domain.usuario.valueobject;

import com.anjos_bolos.anjos_bolos_api.core.application.exception.InvalidArgumentException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

public enum FuncaoUsuarioEnum {

    ADMINISTRADOR("Administrador"),
    GERENTE("Gerente"),
    ATENDENTE("Atendente");

    private final String funcao;

    FuncaoUsuarioEnum(String funcao) {
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
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
                ADMINISTRADOR.funcao,
                GERENTE.funcao,
                ATENDENTE.funcao
        );
    }

    public static FuncaoUsuarioEnum from(String funcao) {
        if (funcao == null) {
            throw new InvalidArgumentException("Função de Usuário não pode ser nula.");
        }
        for (FuncaoUsuarioEnum f : FuncaoUsuarioEnum.values()) {
            if (f.name().equalsIgnoreCase(funcao) || f.getFuncao().equalsIgnoreCase(funcao)) {
                return f;
            }
        }
        throw new InvalidArgumentException("Função de Usuário Inválida: " + funcao + ". Funções de Usuário válidas: " + names());
    }
}