package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.item_pedido.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.item_pedido.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.item_pedido.ItemPedido;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.item_pedido.ItemPedidoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.item_pedido.ItemPedidoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.ItemPedidoEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Itens de Pedido", description = "Operações relacionadas à Entidade de Itens de Pedido")
@RestController
@RequestMapping("/itens-pedido")
public class ItemPedidoController {
    private final CreateItemPedidoUseCase createItemPedidoUseCase;
    private final UpdateItemPedidoUseCase updateItemPedidoUseCase;
    private final DeleteItemPedidoUseCase deleteItemPedidoUseCase;
    private final ListItensPedidoUseCase listItensPedidoUseCase;
    private final GetItemPedidoByIdUseCase getItemPedidoByIdUseCase;
    private final ListItensPedidoByPedidoIdUseCase listItensPedidoByPedidoIdUseCase;

    public ItemPedidoController(CreateItemPedidoUseCase createItemPedidoUseCase, UpdateItemPedidoUseCase updateItemPedidoUseCase, DeleteItemPedidoUseCase deleteItemPedidoUseCase, ListItensPedidoUseCase listItensPedidoUseCase, GetItemPedidoByIdUseCase getItemPedidoByIdUseCase, ListItensPedidoByPedidoIdUseCase listItensPedidoByPedidoIdUseCase) {
        this.createItemPedidoUseCase = createItemPedidoUseCase;
        this.updateItemPedidoUseCase = updateItemPedidoUseCase;
        this.deleteItemPedidoUseCase = deleteItemPedidoUseCase;
        this.listItensPedidoUseCase = listItensPedidoUseCase;
        this.getItemPedidoByIdUseCase = getItemPedidoByIdUseCase;
        this.listItensPedidoByPedidoIdUseCase = listItensPedidoByPedidoIdUseCase;
    }

    @Operation(summary = "Cadastrar novo Item para o Pedido", description = "Cria e salva um novo Item do Pedido no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item do Pedido cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Item do Pedido já existe")
    })
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ItemPedidoResponseDTO> cadastrarItemPedido(@RequestBody @Valid ItemPedidoRequestDTO dto) {
        CreateItemPedidoCommand command = ItemPedidoEntityMapper.toCommand(dto);
        ItemPedido itemPedido = createItemPedidoUseCase.execute(command);

        return ResponseEntity.status(201).body(ItemPedidoEntityMapper.toDTO(itemPedido));
    }

    @Operation(summary = "Listar todos os Itens de Pedidos", description = "Retorna uma lista com todos os Itens de Pedidos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens de Pedidos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Item de Pedido encontrado")
    })
    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ItemPedidoResponseDTO>> listarItensPedido() {
        ListItensPedidoQuery query = new ListItensPedidoQuery();
        List<ItemPedido> itensPedido = listItensPedidoUseCase.execute(query);

        if (itensPedido.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(itensPedido
                .stream()
                .map(ItemPedidoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Item de Pedido por ID", description = "Busca um Item de Pedido que contenha o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de Pedido encontrado"),
            @ApiResponse(responseCode = "204", description = "Item de Pedido não encontrado")
    })
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ItemPedidoResponseDTO> buscarPorIdItemPedido(@PathVariable Integer id) {
        GetItemPedidoByIdQuery query = ItemPedidoEntityMapper.toGetItemPedidoByIdQuery(id);
        ItemPedido itemPedido = getItemPedidoByIdUseCase.execute(query);

        if (itemPedido == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ItemPedidoEntityMapper.toDTO(itemPedido));
    }

    @Operation(summary = "Buscar Itens de Pedido por ID do Pedido", description = "Filtra Itens de Pedido que pertençam ao Pedido com o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Itens de Pedido encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Item de Pedido encontrado")
    })
    @GetMapping("/filtro-pedido/{pedidoId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ItemPedidoResponseDTO>> listarPorIdPedido(@PathVariable Integer pedidoId) {
        ListItensPedidoByPedidoIdQuery query = ItemPedidoEntityMapper.toListItensPedidoByPedidoIdQuery(pedidoId);
        List<ItemPedido> itensPedido = listItensPedidoByPedidoIdUseCase.execute(query);

        if (itensPedido.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(itensPedido
                .stream()
                .map(ItemPedidoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Atualizar Item de Pedido", description = "Atualiza um Item de Pedido existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de Pedido não encontrado"),
            @ApiResponse(responseCode = "409", description = "Item de Pedido já existe")
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ItemPedidoResponseDTO> atualizarPedido(
            @Parameter(description = "ID do Item de Pedido a ser atualizado") @PathVariable Integer id,
            @RequestBody @Valid ItemPedidoRequestDTO dto
    ) {
        UpdateItemPedidoCommand command = ItemPedidoEntityMapper.toCommand(id, dto);
        ItemPedido itemPedido = updateItemPedidoUseCase.execute(command);

        return ResponseEntity.status(200).body(ItemPedidoEntityMapper.toDTO(itemPedido));
    }

    @Operation(summary = "Excluir Item de Pedido", description = "Remove um Item de Pedido do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item de Pedido excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item de Pedido não encontrado")
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> excluirPedido(
            @Parameter(description = "ID do Pedido a ser excluído") @PathVariable Integer id
    ) {
        DeleteItemPedidoCommand command = ItemPedidoEntityMapper.toCommand(id);
        deleteItemPedidoUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }

}