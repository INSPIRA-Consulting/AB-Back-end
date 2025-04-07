package com.anjos_bolos.anjos_bolos_api.service;

import com.anjos_bolos.anjos_bolos_api.entity.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.exception.EntidadeConflitoException;
import com.anjos_bolos.anjos_bolos_api.exception.FalhaAutenticacaoException;
import com.anjos_bolos.anjos_bolos_api.repository.IngredienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {
    private final IngredienteRepository ingredienteRepository;

    public IngredienteService(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    public Ingrediente cadastrar(Ingrediente novoIngrediente) {
        Boolean ingredienteExistePorNome = ingredienteRepository.existsByNome(novoIngrediente.getNome());

        if (ingredienteExistePorNome) {
            throw new EntidadeConflitoException("Já existe um Ingrediente '%s' cadastrado.".formatted(novoIngrediente.getNome()));
        }

        novoIngrediente.setIdIngrediente(null);

        Ingrediente ingredienteCadastrado = ingredienteRepository.save(novoIngrediente);

        return ingredienteCadastrado;
    }

    public List<Ingrediente> listar() {
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();

        return ingredientes;
    }

    public List<Ingrediente> listarPorNome(String nomeIngrediente) {
        List<Ingrediente> ingredientesFiltrados = ingredienteRepository.findByNomeContainsIgnoreCase(nomeIngrediente);

        return ingredientesFiltrados;
    }

    public Ingrediente atualizar(Integer idIngrediente, Ingrediente ingredienteParaAtualizar) {
        Boolean existePorId = ingredienteRepository.existsById(idIngrediente);

        if (!existePorId) {
            throw new FalhaAutenticacaoException("Ingrediente com ID %d não encontrado.".formatted(idIngrediente));
        }

        Boolean existePorNome = ingredienteRepository.existsByNomeEqualsIgnoreCaseAndIdIngredienteNot(
                ingredienteParaAtualizar.getNome(), idIngrediente
        );

        if (existePorNome) {
            throw new EntidadeConflitoException("Já existe um Ingrediente '%s' cadastrado.".formatted(ingredienteParaAtualizar.getNome()));
        }

        ingredienteParaAtualizar.setIdIngrediente(idIngrediente);
        Ingrediente ingredienteAtualizado = ingredienteRepository.save(ingredienteParaAtualizar);

        return ingredienteAtualizado;
    }

    public void excluir(Integer idIngrediente) {
        Boolean existePorId = ingredienteRepository.existsById(idIngrediente);

        if (!existePorId) {
            throw new FalhaAutenticacaoException("Ingrediente com ID %d não encontrado.".formatted(idIngrediente));
        }

        ingredienteRepository.deleteById(idIngrediente);
    }
}
