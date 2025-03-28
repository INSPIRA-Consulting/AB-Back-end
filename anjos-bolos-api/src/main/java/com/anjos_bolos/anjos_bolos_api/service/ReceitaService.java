package com.anjos_bolos.anjos_bolos_api.service;

import com.anjos_bolos.anjos_bolos_api.entity.Ingrediente;
import com.anjos_bolos.anjos_bolos_api.entity.Produto;
import com.anjos_bolos.anjos_bolos_api.entity.Receita;
import com.anjos_bolos.anjos_bolos_api.entity.ReceitaPrimaryKey;
import com.anjos_bolos.anjos_bolos_api.exception.EntidadeConflitoException;
import com.anjos_bolos.anjos_bolos_api.exception.EntidadeNaoEncontradaException;
import com.anjos_bolos.anjos_bolos_api.repository.IngredienteRepository;
import com.anjos_bolos.anjos_bolos_api.repository.ProdutoRepository;
import com.anjos_bolos.anjos_bolos_api.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {
    private final ReceitaRepository receitaRepository;

    private final ProdutoRepository produtoRepository;

    private final IngredienteRepository ingredienteRepository;

    public ReceitaService(ReceitaRepository receitaRepository, ProdutoRepository produtoRepository, IngredienteRepository ingredienteRepository) {
        this.receitaRepository = receitaRepository;
        this.produtoRepository = produtoRepository;
        this.ingredienteRepository = ingredienteRepository;
    }

    public Receita cadastrar(Receita novaReceita) {
        Integer idProduto = novaReceita.getIdReceita().getFkProduto();
        Integer idIngrediente = novaReceita.getIdReceita().getFkIngrediente();

        Optional<Produto> produtoOptional = produtoRepository.findById(idProduto);
        Optional<Ingrediente> ingredienteOptional = ingredienteRepository.findById(idIngrediente);


        if (produtoOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto com ID %d não encontrado.".formatted(idProduto));
        }

        if (ingredienteOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Ingrediente com ID %d não encontrado.".formatted(idIngrediente));
        }

        Produto produto = produtoOptional.get();
        Ingrediente ingrediente = ingredienteOptional.get();

        ReceitaPrimaryKey idReceita = new ReceitaPrimaryKey(produto.getIdProduto(), ingrediente.getIdIngrediente());

        Boolean existeReceita = receitaRepository.existsById(idReceita);

        if (existeReceita) {
            throw new EntidadeConflitoException("Já existe uma Receita do Produto '%s' com o Ingrediente '%s' cadastrada."
                    .formatted(produto.getNome(), ingrediente.getNome()));
        }

        novaReceita = new Receita(idReceita, produto, ingrediente, novaReceita.getQuantidade());

        Receita receitaCadastrada = receitaRepository.save(novaReceita);

        return receitaCadastrada;
    }

    public List<Receita> listar() {
        List<Receita> receitas = receitaRepository.findAll();

        return receitas;
    }

    public Double calcularPreco(Integer idProduto) {
        Boolean existeProduto = produtoRepository.existsById(idProduto);
        List<Receita> receitas = receitaRepository.findAll();

        if (!existeProduto) {
            throw new EntidadeNaoEncontradaException("Produto com ID %d não encontrado.".formatted(idProduto));
        }

        Double valorCusto = receitas.stream()
                .filter(receita -> receita.getProduto().getIdProduto().equals(idProduto))
                .mapToDouble(receita -> receita.getIngrediente().getPreco() * receita.getQuantidade())
                .sum();

            return valorCusto;
    }

    public void excluir(Integer idProduto, Integer idIngrediente) {
        ReceitaPrimaryKey idReceita = new ReceitaPrimaryKey(idProduto, idIngrediente);
        Boolean existePorId = receitaRepository.existsById(idReceita);

        if (!existePorId) {
            throw new EntidadeNaoEncontradaException("Receita não encontrada com Produto de ID %d e Ingrediente de ID %d."
                    .formatted(idProduto, idIngrediente));
        }

        receitaRepository.deleteById(idReceita);
    }

    public Receita buscarReceitaPorProduto(Integer idProduto) {
        Optional<Receita> receita = receitaRepository.findByProduto_IdProduto(idProduto);

        if (receita.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Receita não encontrada com Produto de ID %d.".formatted(idProduto));
        }

        return receita.get();
    }
}
