package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.cliente.CreateClienteCommand;
import com.anjos_bolos.anjos_bolos_api.core.application.command.cliente.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.cliente.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.cliente.Cliente;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.cliente.ClienteResponseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.cliente.ClienteRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.ClienteEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:5173")
public class ClienteController {

    private final CreateClienteUseCase createClienteUseCase;
    private final UpdateClienteUseCase updateClienteUseCase;
    private final DeleteClienteUseCase deleteClienteUseCase;
    private final ListClientesUseCase listClientesUseCase;
    private final GetClienteByIdUseCase getClienteByIdUseCase;
    private final GetClienteByCpfUseCase getClienteByCpfUseCase;
    private final ListClientesByNomeUseCase listClientesByNomeUseCase;

    public ClienteController(CreateClienteUseCase createClienteUseCase, UpdateClienteUseCase updateClienteUseCase, DeleteClienteUseCase deleteClienteUseCase, ListClientesUseCase listClientesUseCase, GetClienteByIdUseCase getClienteByIdUseCase, GetClienteByCpfUseCase getClienteByCpfUseCase, ListClientesByNomeUseCase listClientesByNomeUseCase) {
        this.createClienteUseCase = createClienteUseCase;
        this.updateClienteUseCase = updateClienteUseCase;
        this.deleteClienteUseCase = deleteClienteUseCase;
        this.listClientesUseCase = listClientesUseCase;
        this.getClienteByIdUseCase = getClienteByIdUseCase;
        this.getClienteByCpfUseCase = getClienteByCpfUseCase;
        this.listClientesByNomeUseCase = listClientesByNomeUseCase;
    }

    @Operation(summary = "Cadastrar novo Cliente", description = "Cria e salva um novo Cliente no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Cliente já existe")
    })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO dto) {
        CreateClienteCommand command = ClienteEntityMapper.toCommand(dto);
        Cliente cliente = createClienteUseCase.execute(command);

        return ResponseEntity.status(201).body(ClienteEntityMapper.toDTO(cliente));
    }

    @Operation(summary = "Listar todos os Clientes", description = "Retorna uma lista com todos os Clientes cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Cliente encontrado")
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        ListClientesQuery query = new ListClientesQuery();
        List<Cliente> clientes = listClientesUseCase.execute(query);

        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(clientes
                .stream()
                .map(ClienteEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Cliente por ID", description = "Busca um Cliente que contenha o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "204", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Integer id) {
        GetClienteByIdQuery query = ClienteEntityMapper.toGetClienteByIdQuery(id);
        Cliente cliente = getClienteByIdUseCase.execute(query);

        if (cliente == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ClienteEntityMapper.toDTO(cliente));
    }

    @Operation(summary = "Buscar Cliente por CPF", description = "Busca um Cliente que contenha o CPF informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "204", description = "Cliente não encontrado")
    })
    @GetMapping("/filtro-cpf")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorCpf(@RequestParam String cpf) {
        GetClienteByCpfQuery query = ClienteEntityMapper.toGetClienteByCpfQuery(cpf);
        Cliente cliente = getClienteByCpfUseCase.execute(query);

        if (cliente == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ClienteEntityMapper.toDTO(cliente));
    }

    @Operation(summary = "Buscar Clientes por Nome", description = "Filtra Clientes que contenham parte do Nome informado (sem case sensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Cliente encontrado")
    })
    @GetMapping("/filtro-nome")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesPorNome(@RequestParam String nome) {
        ListClientesByNomeQuery query = ClienteEntityMapper.toListClientesByNomeQuery(nome);
        List<Cliente> clientes = listClientesByNomeUseCase.execute(query);

        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(clientes
                .stream()
                .map(ClienteEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Atualizar Cliente", description = "Atualiza um Cliente existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "409", description = "Cliente com esse nome já existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(
            @Parameter(description = "ID do Cliente a ser atualizado") @PathVariable Integer id,
            @RequestBody @Valid ClienteRequestDTO dto
    ) {
        UpdateClienteCommand command = ClienteEntityMapper.toCommand(id, dto);
        Cliente cliente = updateClienteUseCase.execute(command);

        return ResponseEntity.status(200).body(ClienteEntityMapper.toDTO(cliente));
    }

    @Operation(summary = "Excluir Cliente", description = "Remove um Cliente do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(
            @Parameter(description = "ID do Cliente a ser excluído") @PathVariable Integer id
    ) {
        DeleteClienteCommand command = ClienteEntityMapper.toCommand(id);
        deleteClienteUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }

}