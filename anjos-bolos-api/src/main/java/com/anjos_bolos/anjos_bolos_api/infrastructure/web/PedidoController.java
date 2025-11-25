package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.pedido.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.pedido.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.pedido.Pedido;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido.PedidoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido.PedidoResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.pedido.StatusPedidoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.PedidoEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Pedidos", description = "Operações relacionadas à Entidade de Pedidos")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final CreatePedidoUseCase createPedidoUseCase;
    private final UpdatePedidoUseCase updatePedidoUseCase;
    private final UpdateStatusPedidoUseCase updateStatusPedidoUseCase;
    private final DeletePedidoUseCase deletePedidoUseCase;
    private final ListPedidosUseCase listPedidosUseCase;
    private final GetPedidoByIdUseCase getPedidoByIdUseCase;
    private final ListPedidosByClienteIdUseCase listPedidosByClienteIdUseCase;
    private final ListPedidosByClienteCpfUseCase listPedidosByClienteCpfUseCase;
    private final ListPedidosByDataPedidoUseCase listPedidosByDataPedidoUseCase;
    private final ListPedidosByDataRetiradaUseCase listPedidosByDataRetiradaUseCase;
    private final ListPedidosByDataPagamentoUseCase listPedidosByDataPagamentoUseCase;
    private final ListPedidosByFormaPagamentoUseCase listPedidosByFormaPagamentoUseCase;
    private final ListPedidosByStatusUseCase listPedidosByStatusUseCase;

    public PedidoController(CreatePedidoUseCase createPedidoUseCase, UpdatePedidoUseCase updatePedidoUseCase, UpdateStatusPedidoUseCase updateStatusPedidoUseCase, DeletePedidoUseCase deletePedidoUseCase, ListPedidosUseCase listPedidosUseCase, GetPedidoByIdUseCase getPedidoByIdUseCase, ListPedidosByClienteIdUseCase listPedidosByClienteIdUseCase, ListPedidosByClienteCpfUseCase listPedidosByClienteCpfUseCase, ListPedidosByDataPedidoUseCase listPedidosByDataPedidoUseCase, ListPedidosByDataRetiradaUseCase listPedidosByDataRetiradaUseCase, ListPedidosByDataPagamentoUseCase listPedidosByDataPagamentoUseCase, ListPedidosByFormaPagamentoUseCase listPedidosByFormaPagamentoUseCase, ListPedidosByStatusUseCase listPedidosByStatusUseCase) {
        this.createPedidoUseCase = createPedidoUseCase;
        this.updatePedidoUseCase = updatePedidoUseCase;
        this.updateStatusPedidoUseCase = updateStatusPedidoUseCase;
        this.deletePedidoUseCase = deletePedidoUseCase;
        this.listPedidosUseCase = listPedidosUseCase;
        this.getPedidoByIdUseCase = getPedidoByIdUseCase;
        this.listPedidosByClienteIdUseCase = listPedidosByClienteIdUseCase;
        this.listPedidosByClienteCpfUseCase = listPedidosByClienteCpfUseCase;
        this.listPedidosByDataPedidoUseCase = listPedidosByDataPedidoUseCase;
        this.listPedidosByDataRetiradaUseCase = listPedidosByDataRetiradaUseCase;
        this.listPedidosByDataPagamentoUseCase = listPedidosByDataPagamentoUseCase;
        this.listPedidosByFormaPagamentoUseCase = listPedidosByFormaPagamentoUseCase;
        this.listPedidosByStatusUseCase = listPedidosByStatusUseCase;
    }

    @Operation(summary = "Cadastrar novo Pedido", description = "Cria e salva um novo Pedido no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Pedido já existe")
    })
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PedidoResponseDTO> cadastrarPedido(@RequestBody @Valid PedidoRequestDTO dto) {
        CreatePedidoCommand command = PedidoEntityMapper.toCommand(dto);
        Pedido pedido = createPedidoUseCase.execute(command);

        return ResponseEntity.status(201).body(PedidoEntityMapper.toDTO(pedido));
    }

    @Operation(summary = "Listar todos os Pedidos", description = "Retorna uma lista com todos os Pedidos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Pedido encontrado")
    })
    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        ListPedidosQuery query = new ListPedidosQuery();
        List<Pedido> pedidos = listPedidosUseCase.execute(query);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(pedidos
                .stream()
                .map(PedidoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Pedido por ID", description = "Busca um Pedido que contenha o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "204", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PedidoResponseDTO> buscarPorIdPedido(@PathVariable Integer id) {
        GetPedidoByIdQuery query = PedidoEntityMapper.toGetPedidoByIdQuery(id);
        Pedido pedido = getPedidoByIdUseCase.execute(query);

        if (pedido == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(PedidoEntityMapper.toDTO(pedido));
    }

    @Operation(summary = "Buscar Pedidos por ID do Cliente", description = "Filtra Pedidos que contenham o Cliente com o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Pedido encontrado")
    })
    @GetMapping("/filtro-cliente/{clienteId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorIdCliente(@PathVariable Integer clienteId) {
        ListPedidosByClienteIdQuery query = PedidoEntityMapper.toListPedidosByClienteIdQuery(clienteId);
        List<Pedido> pedidos = listPedidosByClienteIdUseCase.execute(query);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(pedidos
                .stream()
                .map(PedidoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Pedidos por CPF do Cliente", description = "Filtra Pedidos que contenham o CPF do Cliente informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Pedido encontrado")
    })
    @GetMapping("/filtro-cliente")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorNomePedido(@RequestParam String clienteCpf) {
        ListPedidosByClienteCpfQuery query = PedidoEntityMapper.toListPedidosByClienteCpfQuery(clienteCpf);
        List<Pedido> pedidos = listPedidosByClienteCpfUseCase.execute(query);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(pedidos
                .stream()
                .map(PedidoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Pedidos por Data do Pedido", description = "Filtra Pedidos que contenham a Data de Pedido informada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Pedido encontrado")
    })
    @GetMapping("/filtro-data-pedido")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorDataPedido(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataPedido) {
        ListPedidosByDataPedidoQuery query = PedidoEntityMapper.toListPedidosByDataPedidoQuery(dataPedido);
        List<Pedido> pedidos = listPedidosByDataPedidoUseCase.execute(query);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(pedidos
                .stream()
                .map(PedidoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Pedidos por Data de Retirada", description = "Filtra Pedidos que contenham a Data de Retirada informada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Pedido encontrado")
    })
    @GetMapping("/filtro-data-retirada")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorDataRetirada(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataPedido,
                                                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataRetirada) {
        ListPedidosByDataRetiradaQuery query = PedidoEntityMapper.toListPedidosByDataRetiradaQuery(dataPedido, dataRetirada);
        List<Pedido> pedidos = listPedidosByDataRetiradaUseCase.execute(query);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(pedidos
                .stream()
                .map(PedidoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Pedidos por Data de Pagamento", description = "Filtra Pedidos que contenham a Data de Pagamento informada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Pedido encontrado")
    })
    @GetMapping("/filtro-data-pagamento")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorDataPagamento(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dataPagamento) {
        ListPedidosByDataPagamentoQuery query = PedidoEntityMapper.toListPedidosByDataPagamentoQuery(dataPagamento);
        List<Pedido> pedidos = listPedidosByDataPagamentoUseCase.execute(query);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(pedidos
                .stream()
                .map(PedidoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Pedidos por Forma de Pagamento", description = "Filtra Pedidos que contenham a Forma de Pagamento informada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Pedido encontrado")
    })
    @GetMapping("/filtro-forma-pagamento")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorFormaPagamento(@RequestParam String formaPagamento) {
        ListPedidosByFormaPagamentoQuery query = PedidoEntityMapper.toListPedidosByFormaPagamentoQuery(formaPagamento);
        List<Pedido> pedidos = listPedidosByFormaPagamentoUseCase.execute(query);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(pedidos
                .stream()
                .map(PedidoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Pedidos por Status do Pedido", description = "Filtra Pedidos que contenham o Status informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Pedido encontrado")
    })
    @GetMapping("/filtro-status")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorStatus(@RequestParam String status) {
        ListPedidosByStatusQuery query = PedidoEntityMapper.toListPedidosByStatusQuery(status);
        List<Pedido> pedidos = listPedidosByStatusUseCase.execute(query);

        if (pedidos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(pedidos
                .stream()
                .map(PedidoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Atualizar Pedido", description = "Atualiza um Pedido existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
    })
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PedidoResponseDTO> atualizarPedido(
            @Parameter(description = "ID do Pedido a ser atualizado") @PathVariable Integer id,
            @RequestBody @Valid PedidoRequestDTO dto
    ) {
        UpdatePedidoCommand command = PedidoEntityMapper.toCommand(id, dto);
        Pedido pedido = updatePedidoUseCase.execute(command);

        return ResponseEntity.status(200).body(PedidoEntityMapper.toDTO(pedido));
    }

    @Operation(summary = "Atualizar Status do Pedido", description = "Atualiza o Status (e outras informações) de um Pedido existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
    })
    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<PedidoResponseDTO> atualizarStatusPedido(
            @Parameter(description = "ID do Pedido a ser atualizado") @PathVariable Integer id,
            @RequestBody @Valid StatusPedidoRequestDTO dto
    ) {
        UpdateStatusPedidoCommand command = PedidoEntityMapper.toCommand(id, dto);
        Pedido pedido = updateStatusPedidoUseCase.execute(command);

        return ResponseEntity.status(200).body(PedidoEntityMapper.toDTO(pedido));
    }

    @Operation(summary = "Excluir Pedido", description = "Remove um Pedido do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> excluirPedido(
            @Parameter(description = "ID do Pedido a ser excluído") @PathVariable Integer id
    ) {
        DeletePedidoCommand command = PedidoEntityMapper.toCommand(id);
        deletePedidoUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }

}