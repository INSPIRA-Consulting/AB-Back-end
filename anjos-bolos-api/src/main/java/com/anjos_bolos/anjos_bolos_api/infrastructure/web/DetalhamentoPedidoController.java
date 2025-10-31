package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.detalhamento_pedido.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.detalhamento_pedido.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.detalhamento_pedido.DetalhamentoPedido;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.detalhamento_pedido.DetalhamentoPedidoReceitasResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.detalhamento_pedido.DetalhamentoPedidoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.detalhamento_pedido.DetalhamentoPedidoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.DetalhamentoPedidoEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Detalhamento Pedido (Encomendas)", description = "Operações relacionadas à Entidade de Detalhamento de Pedido (Enconmendas)")
@RestController
@RequestMapping("detalhamentos-pedidos")
public class DetalhamentoPedidoController {

    private final CreateDetalhamentoPedidoUseCase createDetalhamentoPedidoUseCase;
    private final UpdateDetalhamentoPedidoUseCase updateDetalhamentoPedidoUseCase;
    private final DeleteDetalhamentoPedidoUseCase deleteDetalhamentoPedidoUseCase;
    private final ListDetalhamentosPedidosUseCase listDetalhamentosPedidosUseCase;
    private final GetDetalhamentoPedidoByIdUseCase getDetalhamentoPedidoByIdUseCase;
    private final ListDetalhamentoPedidoByItemPedidoIdUseCase listDetalhamentoPedidoByItemPedidoIdUseCase;

    public DetalhamentoPedidoController(CreateDetalhamentoPedidoUseCase createDetalhamentoPedidoUseCase, UpdateDetalhamentoPedidoUseCase updateDetalhamentoPedidoUseCase, DeleteDetalhamentoPedidoUseCase deleteDetalhamentoPedidoUseCase, ListDetalhamentosPedidosUseCase listDetalhamentosPedidosUseCase, GetDetalhamentoPedidoByIdUseCase getDetalhamentoPedidoByIdUseCase, ListDetalhamentoPedidoByItemPedidoIdUseCase listDetalhamentoPedidoByItemPedidoIdUseCase) {
        this.createDetalhamentoPedidoUseCase = createDetalhamentoPedidoUseCase;
        this.updateDetalhamentoPedidoUseCase = updateDetalhamentoPedidoUseCase;
        this.deleteDetalhamentoPedidoUseCase = deleteDetalhamentoPedidoUseCase;
        this.listDetalhamentosPedidosUseCase = listDetalhamentosPedidosUseCase;
        this.getDetalhamentoPedidoByIdUseCase = getDetalhamentoPedidoByIdUseCase;
        this.listDetalhamentoPedidoByItemPedidoIdUseCase = listDetalhamentoPedidoByItemPedidoIdUseCase;
    }

    @Operation(summary = "Cadastrar novo Detalhamento de Pedido", description = "Cria e salva um novo Detalhamento de Pedido no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Detalhamento de Pedido cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Detalhamento de Pedido já existe")
    })
    @PostMapping
    public ResponseEntity<DetalhamentoPedidoResponseDTO> cadastrarDetalhamentoPedido(@RequestBody @Valid DetalhamentoPedidoRequestDTO dto) {
        CreateDetalhamentoPedidoCommand command = DetalhamentoPedidoEntityMapper.toCommand(dto);
        DetalhamentoPedido detalhamentoPedido = createDetalhamentoPedidoUseCase.execute(command);

        return ResponseEntity.status(201).body(DetalhamentoPedidoEntityMapper.toDTO(detalhamentoPedido));
    }

    @Operation(summary = "Listar todos os Detalhamentos de Pedido", description = "Retorna uma lista com todos os Detalhamentos de Pedido cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhamentos de Pedido encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Detalhamento de Pedido encontrado")
    })
    @GetMapping
    public ResponseEntity<List<DetalhamentoPedidoResponseDTO>> listarDetalhamentosPedidos() {
        ListDetalhamentosPedidoQuery query = new ListDetalhamentosPedidoQuery();
        List<DetalhamentoPedido> detalhamentosPedidos = listDetalhamentosPedidosUseCase.execute(query);

        if (detalhamentosPedidos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(detalhamentosPedidos
                .stream()
                .map(DetalhamentoPedidoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Detalhamento de Pedido por ID", description = "Busca um Detalhamento de Pedido que contenha o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "204", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DetalhamentoPedidoResponseDTO> buscarPorIdDetalhamentoPedido(@PathVariable Integer id) {
        GetDetalhamentoPedidoByIdQuery query = DetalhamentoPedidoEntityMapper.toGetDetalhamentoPedidoByIdQuery(id);
        DetalhamentoPedido detalhamentoPedido = getDetalhamentoPedidoByIdUseCase.execute(query);

        if (detalhamentoPedido == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(DetalhamentoPedidoEntityMapper.toDTO(detalhamentoPedido));
    }

    @Operation(summary = "Buscar Detalhamentos de Pedido por ID Item de Pedido", description = "Filtra Detalhamentos de Pedido que contenham o ID do Item de Pedido informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Pedido encontrado")
    })
    @GetMapping("/filtro-item-pedido/{itemPedidoId}")
    public ResponseEntity<DetalhamentoPedidoReceitasResponseDTO> listarPorIdItemPedido(@PathVariable Integer itemPedidoId) {
        ListDetalhamentoPedidoByItemPedidoIdQuery query = DetalhamentoPedidoEntityMapper.toListDetalhamentoPedidoByItemPedidoIdQuery(itemPedidoId);
        List<DetalhamentoPedido> detalhamentosPedido = listDetalhamentoPedidoByItemPedidoIdUseCase.execute(query);

        if (detalhamentosPedido.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(DetalhamentoPedidoEntityMapper.toDTO(itemPedidoId, detalhamentosPedido));
    }

    @Operation(summary = "Atualizar Detalhamento de Pedido", description = "Atualiza um Detalhamento de Pedido existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalhamento de Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Detalhamento de Pedido não encontrado"),
            @ApiResponse(responseCode = "409", description = "Detalhamento de Pedido com esse nome já existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DetalhamentoPedidoResponseDTO> atualizarDetalhamentoPedido(
            @Parameter(description = "ID do Detalhamento de Pedido a ser atualizado") @PathVariable Integer id,
            @RequestBody @Valid DetalhamentoPedidoRequestDTO dto
    ) {
        UpdateDetalhamentoPedidoCommand command = DetalhamentoPedidoEntityMapper.toCommand(id, dto);
        DetalhamentoPedido detalhamentoPedido = updateDetalhamentoPedidoUseCase.execute(command);

        return ResponseEntity.status(200).body(DetalhamentoPedidoEntityMapper.toDTO(detalhamentoPedido));
    }

    @Operation(summary = "Excluir Detalhamento de Pedido", description = "Remove um Detalhamento de Pedido do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalhamento de Pedido excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Detalhamento de Pedido não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDetalhamentoPedido(
            @Parameter(description = "ID do Detalhamento de Pedido a ser excluído") @PathVariable Integer id
    ) {
        DeleteDetalhamentoPedidoCommand command = DetalhamentoPedidoEntityMapper.toCommand(id);
        deleteDetalhamentoPedidoUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }

}