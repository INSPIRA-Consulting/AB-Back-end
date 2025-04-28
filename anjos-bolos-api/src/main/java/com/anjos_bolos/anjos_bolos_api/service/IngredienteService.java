package com.anjos_bolos.anjos_bolos_api.service;

import com.anjos_bolos.anjos_bolos_api.dto.ingrediente.IngredienteAtualizacaoDto;
import com.anjos_bolos.anjos_bolos_api.dto.IngredienteCadastroDto;
import com.anjos_bolos.anjos_bolos_api.dto.ingrediente.IngredienteResponseDto;
import com.anjos_bolos.anjos_bolos_api.entity.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.exception.EntidadeConflitoException;
import com.anjos_bolos.anjos_bolos_api.exception.FalhaAutenticacaoException;
import com.anjos_bolos.anjos_bolos_api.repository.IngredienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredienteService {
    private final IngredienteRepository ingredienteRepository;

    public IngredienteService(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    public IngredienteResponseDto cadastrar(IngredienteCadastroDto novoIngredienteDto) {
        Boolean ingredienteExistePorNome = ingredienteRepository.existsByNome(novoIngredienteDto.getNome());

        if (ingredienteExistePorNome) {
            throw new EntidadeConflitoException("Já existe um Ingrediente '%s' cadastrado.".formatted(novoIngredienteDto.getNome()));
        }

        Ingrediente novoIngrediente = new Ingrediente();
        novoIngrediente.setNome(novoIngredienteDto.getNome());
        novoIngrediente.setMedida(novoIngredienteDto.getMedida());
        novoIngrediente.setPreco(novoIngredienteDto.getPreco());

        Ingrediente ingredienteCadastrado = ingredienteRepository.save(novoIngrediente);

        return new IngredienteResponseDto(
                ingredienteCadastrado.getIdIngrediente(),
                ingredienteCadastrado.getNome(),
                ingredienteCadastrado.getMedida(),
                ingredienteCadastrado.getPreco()
        );
    }

    public List<IngredienteResponseDto> listar() {
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();

        return ingredientes.stream()
                .map(ingrediente -> new IngredienteResponseDto(
                        ingrediente.getIdIngrediente(),
                        ingrediente.getNome(),
                        ingrediente.getMedida(),
                        ingrediente.getPreco()
                ))
                .collect(Collectors.toList());
    }

    public List<IngredienteResponseDto> listarPorNome(String nomeIngrediente) {
        List<Ingrediente> ingredientesFiltrados = ingredienteRepository.findByNomeContainsIgnoreCase(nomeIngrediente);

        return ingredientesFiltrados.stream()
                .map(ingrediente -> new IngredienteResponseDto(
                        ingrediente.getIdIngrediente(),
                        ingrediente.getNome(),
                        ingrediente.getMedida(),
                        ingrediente.getPreco()
                ))
                .collect(Collectors.toList());
    }

    public IngredienteResponseDto atualizar(Integer idIngrediente, IngredienteAtualizacaoDto ingredienteParaAtualizarDto) {
        Boolean existePorId = ingredienteRepository.existsById(idIngrediente);

        if (!existePorId) {
            throw new FalhaAutenticacaoException("Ingrediente com ID %d não encontrado.".formatted(idIngrediente));
        }

        Boolean existePorNome = ingredienteRepository.existsByNomeEqualsIgnoreCaseAndIdIngredienteNot(
                ingredienteParaAtualizarDto.getNome(), idIngrediente
        );

        if (existePorNome) {
            throw new EntidadeConflitoException("Já existe um Ingrediente '%s' cadastrado.".formatted(ingredienteParaAtualizarDto.getNome()));
        }

        Ingrediente ingredienteParaAtualizar = new Ingrediente();
        ingredienteParaAtualizar.setIdIngrediente(idIngrediente);
        ingredienteParaAtualizar.setNome(ingredienteParaAtualizarDto.getNome());
        ingredienteParaAtualizar.setMedida(ingredienteParaAtualizarDto.getMedida());
        ingredienteParaAtualizar.setPreco(ingredienteParaAtualizarDto.getPreco());

        Ingrediente ingredienteAtualizado = ingredienteRepository.save(ingredienteParaAtualizar);

        return new IngredienteResponseDto(
                ingredienteAtualizado.getIdIngrediente(),
                ingredienteAtualizado.getNome(),
                ingredienteAtualizado.getMedida(),
                ingredienteAtualizado.getPreco()
        );
    }

    public void excluir(Integer idIngrediente) {
        Boolean existePorId = ingredienteRepository.existsById(idIngrediente);

        if (!existePorId) {
            throw new FalhaAutenticacaoException("Ingrediente com ID %d não encontrado.".formatted(idIngrediente));
        }

        ingredienteRepository.deleteById(idIngrediente);
    }
}
