package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.adapters;

import com.anjos_bolos.anjos_bolos_api.core.adapters.DashboardGateway;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.MargemLucroProdutoDTO;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.PedidosFaturamentoDTO;
import com.anjos_bolos.anjos_bolos_api.core.domain.dashboard.ProdutoVendidoDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.DashboardMapper;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.DashboardJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardJpaAdapter implements DashboardGateway {

    private final DashboardJpaRepository repository;

    public DashboardJpaAdapter(DashboardJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public MargemLucroProdutoDTO getMenorMargemLucroProdutos(Integer limit) {
        return DashboardMapper.toDTO(repository.getMenorMargemLucroProdutos(limit));
    }

    @Override
    public MargemLucroProdutoDTO getMaiorMargemLucroProdutos(Integer limit) {
        return DashboardMapper.toDTO(repository.getMaiorMargemLucroProdutos(limit));
    }

    @Override
    public List<ProdutoVendidoDTO> listProdutosMaisVendidos(LocalDate inicio, LocalDate fim, Integer limit) {
        return repository.listProdutosMaisVendidos(inicio, fim, limit)
                .stream()
                .map(DashboardMapper::toDTO)
                .toList();
    }

    @Override
    public PedidosFaturamentoDTO getPedidosFaturamento(LocalDate inicio, LocalDate fim) {
         return DashboardMapper.toDTO(repository.getPedidosFaturamento(inicio, fim));
    }

    @Override
    public String getProdutoMaisVendido(LocalDate inicio, LocalDate fim, Integer limit) {
        return repository.getProdutoMaisVendido(inicio, fim, limit);
    }

    @Override
    public String getDiaSemanaComMaisVendas(LocalDate inicio, LocalDate fim, Integer limit) {
        return repository.getDiaSemanaComMaisVendas(inicio, fim, limit);
    }
}
