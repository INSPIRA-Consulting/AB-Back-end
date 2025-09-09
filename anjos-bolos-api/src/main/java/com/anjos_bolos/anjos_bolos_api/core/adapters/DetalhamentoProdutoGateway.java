package com.anjos_bolos.anjos_bolos_api.core.adapters;

import com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido.DetalhamentoPedido;
import com.anjos_bolos.anjos_bolos_api.core.domain.usuario.Usuario;

import java.util.List;

public interface DetalhamentoProdutoGateway {
    DetalhamentoPedido save(DetalhamentoPedido usuario);

    boolean existsByCpf(String cpf);

    List<DetalhamentoPedido> findAll();

    DetalhamentoPedido findById(Integer id);

    DetalhamentoPedido findByCpf(String cpf);

    List<DetalhamentoPedido> findByNome(String nome);

    void update(DetalhamentoPedido usuario);

    void delete(Integer id);
}
