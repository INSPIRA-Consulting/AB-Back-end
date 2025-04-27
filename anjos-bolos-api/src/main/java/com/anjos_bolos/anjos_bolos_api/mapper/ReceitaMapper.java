package com.anjos_bolos.anjos_bolos_api.mapper;

import com.anjos_bolos.anjos_bolos_api.dto.receita.ReceitaAtualizacaoDto;
import com.anjos_bolos.anjos_bolos_api.dto.receita.ReceitaCadastroDto;
import com.anjos_bolos.anjos_bolos_api.dto.receita.ReceitaListagemDto;
import com.anjos_bolos.anjos_bolos_api.dto.receita.ReceitaResponseDto;
import com.anjos_bolos.anjos_bolos_api.entity.Receita;
import com.anjos_bolos.anjos_bolos_api.entity.Usuario;

import java.util.List;

public class ReceitaMapper {

    public static ReceitaResponseDto toResponse(Receita receita) {
        if (receita == null) {
            return null;
        }

        ReceitaResponseDto dto = new ReceitaResponseDto();
        dto.setIdReceita(receita.getIdReceita());
        dto.setQuantidade(receita.getQuantidade());
        dto.setProduto(receita.getProduto());
        dto.setIngrediente(receita.getIngrediente());
        return dto;
    }

    public static ReceitaListagemDto toListagemDto(Receita receita) {
        if (receita == null) {
            return null;
        }

        return new ReceitaListagemDto(
                receita.getIdReceita(),
                receita.getProduto(),
                receita.getIngrediente(),
                receita.getQuantidade()
        );
    }

    public static List<ReceitaListagemDto> toListagemDtos(List<Receita> receitas) {
        if (receitas == null) {
            return null;
        }

        return receitas.stream()
                .map(ReceitaMapper::toListagemDto)
                .toList();

    }

    public static Receita toEntity(ReceitaCadastroDto dto) {
        if (dto == null) {
            return null;
        }

        Receita receita = new Receita();
        receita.setProduto(dto.getProduto());
        receita.setIngrediente(dto.getIngrediente());
        receita.setQuantidade(dto.getQuantidade());
        return receita;
    }

    public static Receita toEntity(ReceitaAtualizacaoDto dto) {
        if (dto == null) {
            return null;
        }

        Receita receita = new Receita();

        if(receita.getProduto() != null){
            receita.setProduto(dto.getProduto());
        }
        if(receita.getIngrediente() != null){
            receita.setIngrediente(dto.getIngrediente());
        }
        if(receita.getQuantidade() != null){
            receita.setQuantidade(dto.getQuantidade());
        }
        return receita;
    }



}
