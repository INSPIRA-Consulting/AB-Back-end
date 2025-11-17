package com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.implementation;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.MargemLucroProdutoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.PedidosFaturamentoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.ProdutoVendidoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.VendasDiaSemanaResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.DashboardJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class DashboardJpaRepositoryImpl implements DashboardJpaRepository {

    private final EntityManager entityManager;

    public DashboardJpaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public MargemLucroProdutoResponseDTO getMenorMargemLucroProdutos(Integer limit) {
        String jpql = """
                    SELECT NEW com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.MargemLucroProdutoResponseDTO(
                        p.nome,
                        ROUND(((p.precoFinal - p.custoProducao) / p.precoFinal * 100), 2)
                    )
                    FROM ProdutoEntity p
                    ORDER BY ((p.precoFinal - p.custoProducao) / p.precoFinal) ASC
                    """;

        TypedQuery<MargemLucroProdutoResponseDTO> query = entityManager.createQuery(jpql, MargemLucroProdutoResponseDTO.class);

        query.setMaxResults(limit);

        List<MargemLucroProdutoResponseDTO> results = query.getResultList();

        return results.getFirst();
    }

    @Override
    public MargemLucroProdutoResponseDTO getMaiorMargemLucroProdutos(Integer limit) {
        String jpql = """
                    SELECT NEW com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.MargemLucroProdutoResponseDTO(
                        p.nome,
                        ROUND(((p.precoFinal - p.custoProducao) / p.precoFinal * 100), 2)
                    )
                    FROM ProdutoEntity p
                    ORDER BY ((p.precoFinal - p.custoProducao) / p.precoFinal) DESC
                    """;

        TypedQuery<MargemLucroProdutoResponseDTO> query = entityManager.createQuery(jpql, MargemLucroProdutoResponseDTO.class);

        query.setMaxResults(limit);

        List<MargemLucroProdutoResponseDTO> results = query.getResultList();

        return results.getFirst();
    }

    @Override
    public List<ProdutoVendidoResponseDTO> listProdutosMaisVendidos(LocalDate inicio, LocalDate fim, Integer limit) {
        String jpql = """
                    SELECT NEW com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.ProdutoVendidoResponseDTO(
                        p.nome,
                        SUM(ip.quantidade),
                        c.nome
                    )
                    FROM PedidoEntity pd, ItemPedidoEntity ip, ProdutoEntity p, CategoriaProdutoEntity c
                    WHERE ip.pedido = pd
                        AND ip.produto = p
                        AND p.categoriaProduto = c
                        AND pd.dataPedido BETWEEN :inicio AND :fim
                        AND pd.status = 'FINALIZADO'
                    GROUP BY p.id, p.nome, c.nome
                    ORDER BY SUM(ip.quantidade) DESC
                    """;

        TypedQuery<ProdutoVendidoResponseDTO> query = entityManager.createQuery(jpql, ProdutoVendidoResponseDTO.class);

        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(LocalTime.MAX);

        query.setParameter("inicio", inicioDateTime);
        query.setParameter("fim", fimDateTime);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public List<PedidosFaturamentoResponseDTO> getPedidosFaturamento(LocalDate inicio, LocalDate fim) {
        String jpql = """
                    SELECT NEW com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.PedidosFaturamentoResponseDTO(
                        COUNT(DISTINCT pd.id),
                        SUM(ip.quantidade),
                        ROUND(SUM(ip.precoUnitario * ip.quantidade), 2),
                        ROUND(SUM(ip.custoProducao * ip.quantidade), 2),
                        CAST(pd.dataRetirada AS LocalDate)
                    )
                    FROM PedidoEntity pd, ItemPedidoEntity ip, ProdutoEntity p
                    WHERE ip.pedido = pd
                        AND ip.produto = p
                        AND pd.dataPedido BETWEEN :inicio AND :fim
                        AND pd.status = 'FINALIZADO'
                    GROUP BY CAST(pd.dataRetirada AS LocalDate)
                    """;


        TypedQuery<PedidosFaturamentoResponseDTO> query = entityManager.createQuery(jpql, PedidosFaturamentoResponseDTO.class);

        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(LocalTime.MAX);

        query.setParameter("inicio", inicioDateTime);
        query.setParameter("fim", fimDateTime);

        return query.getResultList();
    }

    @Override
    public String getProdutoMaisVendido(LocalDate inicio, LocalDate fim, Integer limit) {
        String jpql = """
                    SELECT p.nome
                    FROM PedidoEntity pd, ItemPedidoEntity ip, ProdutoEntity p
                    WHERE ip.pedido = pd
                        AND ip.produto = p
                        AND pd.dataPedido BETWEEN :inicio AND :fim
                        AND pd.status = 'FINALIZADO'
                    GROUP BY p.id, p.nome
                    ORDER BY SUM(ip.quantidade) DESC
                    """;

        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);

        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(LocalTime.MAX);

        query.setParameter("inicio", inicioDateTime);
        query.setParameter("fim", fimDateTime);
        query.setMaxResults(limit);

        List<String> results =  query.getResultList();

        return results.getFirst();
    }

    @Override
    public List<VendasDiaSemanaResponseDTO> listVendasPorDiaSemana(LocalDate inicio, LocalDate fim) {
        String jpql = """
                    SELECT NEW com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.dashboard.VendasDiaSemanaResponseDTO(
                        CASE FUNCTION('WEEKDAY', pd.dataPedido)
                            WHEN 0 THEN 'Segunda-feira'
                            WHEN 1 THEN 'Terça-feira'
                            WHEN 2 THEN 'Quarta-feira'
                            WHEN 3 THEN 'Quinta-feira'
                            WHEN 4 THEN 'Sexta-feira'
                            WHEN 5 THEN 'Sábado'
                            WHEN 6 THEN 'Domingo'
                        END,
                        COUNT(pd.id)
                    )
                    FROM PedidoEntity pd
                    WHERE pd.dataPedido BETWEEN :inicio AND :fim
                        AND pd.status = 'FINALIZADO'
                    GROUP BY FUNCTION('WEEKDAY', pd.dataPedido), 
                             CASE FUNCTION('WEEKDAY', pd.dataPedido)
                                 WHEN 0 THEN 'Segunda-feira'
                                 WHEN 1 THEN 'Terça-feira'
                                 WHEN 2 THEN 'Quarta-feira'
                                 WHEN 3 THEN 'Quinta-feira'
                                 WHEN 4 THEN 'Sexta-feira'
                                 WHEN 5 THEN 'Sábado'
                                 WHEN 6 THEN 'Domingo'
                             END
                    ORDER BY FUNCTION('WEEKDAY', pd.dataPedido)
                    """;

        TypedQuery<VendasDiaSemanaResponseDTO> query = entityManager.createQuery(jpql, VendasDiaSemanaResponseDTO.class);
        query.setParameter("inicio", inicio.atStartOfDay());
        query.setParameter("fim", fim.atTime(LocalTime.MAX));

        return query.getResultList();
    }

}