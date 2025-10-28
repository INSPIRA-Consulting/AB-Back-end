package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.application.command.produto.*;
import com.anjos_bolos.anjos_bolos_api.core.application.usecase.produto.*;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.config.aws.s3.S3UploadService;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoRequestDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.produto.ProdutoRespoonseDTO;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.mapper.ProdutoEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final CreateProdutoUseCase createProdutoUseCase;
    private final UpdateProdutoUseCase updateProdutoUseCase;
    private final DeleteProdutoUseCase deleteProdutoUseCase;
    private final ListProdutosUseCase listProdutosUseCase;
    private final ListProdutosPagebleUseCase listProdutosPagebleUseCase;
    private final GetProdutoByIdUseCase getProdutoByIdUseCase;
    private final ListProdutosByNomeUseCase listProdutosByNomeUseCase;
    private final ListProdutosByCategoriaProdutoIdUseCase listProdutosByCategoriaProdutoIdUseCase;
    private final S3UploadService s3UploadService;

    public ProdutoController(CreateProdutoUseCase createProdutoUseCase, UpdateProdutoUseCase updateProdutoUseCase, DeleteProdutoUseCase deleteProdutoUseCase, ListProdutosUseCase listProdutosUseCase, ListProdutosPagebleUseCase listProdutosPagebleUseCase, GetProdutoByIdUseCase getProdutoByIdUseCase, ListProdutosByNomeUseCase listProdutosByNomeUseCase, ListProdutosByCategoriaProdutoIdUseCase listProdutosByCategoriaProdutoIdUseCase, S3UploadService s3UploadService) {
        this.createProdutoUseCase = createProdutoUseCase;
        this.updateProdutoUseCase = updateProdutoUseCase;
        this.deleteProdutoUseCase = deleteProdutoUseCase;
        this.listProdutosUseCase = listProdutosUseCase;
        this.listProdutosPagebleUseCase = listProdutosPagebleUseCase;
        this.getProdutoByIdUseCase = getProdutoByIdUseCase;
        this.listProdutosByNomeUseCase = listProdutosByNomeUseCase;
        this.listProdutosByCategoriaProdutoIdUseCase = listProdutosByCategoriaProdutoIdUseCase;
        this.s3UploadService = s3UploadService;
    }

    @Operation(summary = "Cadastrar novo Produto", description = "Cria e salva um novo Produto no Banco de Dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Produto já existe")
    })
    @PostMapping
    public ResponseEntity<ProdutoRespoonseDTO> cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO dto) {
        CreateProdutoCommand command = ProdutoEntityMapper.toCommand(dto);
        Produto produto = createProdutoUseCase.execute(command);

        return ResponseEntity.status(201).body(ProdutoEntityMapper.toDTO(produto));
    }

    @Operation(summary = "Upload de imagem do Produto", description = "Faz upload da imagem de um Produto existente para o S3.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem enviada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/{id}/imagem")
    public ResponseEntity<String> uploadImagemProduto(@PathVariable Integer id,
                                                                   @RequestParam("imagem") MultipartFile imagem) {
        try {
            // Buscar o produto para obter o nome
            GetProdutoByIdQuery query = ProdutoEntityMapper.toGetProdutoByIdQuery(id);
            Produto produto = getProdutoByIdUseCase.execute(query);

            if (produto == null) {
                return ResponseEntity.status(404).build();
            }

            // Gerar nome do arquivo usando o nome do produto
            String nomeArquivo = ProdutoEntityMapper.toFileName(produto.getNome());

            // Upload para S3
            String imageUrl = s3UploadService.uploadFile(nomeArquivo, imagem.getBytes());

            return ResponseEntity.status(201).body(imageUrl);

        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Listar todos os Produtos", description = "Retorna uma lista com todos os Produtos cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Produto encontrado")
    })
    @GetMapping
    public ResponseEntity<Page<ProdutoRespoonseDTO>> listarProdutos(Pageable paginacao) {
        ListProdutosPagebleQuery query = new ListProdutosPagebleQuery(paginacao);
        Page<Produto> produtos = listProdutosPagebleUseCase.execute(query);

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos
                .map(ProdutoEntityMapper::toDTO));
    }

    @Operation(summary = "Buscar Produto por ID", description = "Busca um Produto que contenha o ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "204", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoRespoonseDTO> buscarPorIdProduto(@PathVariable Integer id) {
        GetProdutoByIdQuery query = ProdutoEntityMapper.toGetProdutoByIdQuery(id);
        Produto produto = getProdutoByIdUseCase.execute(query);

        if (produto == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ProdutoEntityMapper.toDTO(produto));
    }

    @Operation(summary = "Buscar Produtos por nome", description = "Filtra Produtos que contenham parte do nome informado (sem case sensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Produto encontrado")
    })
    @GetMapping("/filtro-nome")
    public ResponseEntity<List<ProdutoRespoonseDTO>> listarPorNomeProduto(@RequestParam String nome) {
        ListProdutosByNomeQuery query = ProdutoEntityMapper.toListProdutosByNomeQuery(nome);
        List<Produto> produtos = listProdutosByNomeUseCase.execute(query);

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(produtos
                .stream()
                .map(ProdutoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Buscar Produtos por Categoria de Produto", description = "Filtra Produtos que contenham a Categoria de Produto informada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum Produto encontrado")
    })
    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<ProdutoRespoonseDTO>> buscarPorIdCategoriaProduto(@PathVariable Integer id) {
        ListProdutosByCategoriaProdutoIdQuery query = ProdutoEntityMapper.toListProdutosByCategoriaProdutoIdQuery(id);
        List<Produto> produtos = listProdutosByCategoriaProdutoIdUseCase.execute(query);

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(produtos
                .stream()
                .map(ProdutoEntityMapper::toDTO)
                .toList());
    }

    @Operation(summary = "Atualizar Produto", description = "Atualiza um Produto existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "409", description = "Produto com esse nome já existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoRespoonseDTO> atualizarProduto(
            @Parameter(description = "ID do Produto a ser atualizado") @PathVariable Integer id,
            @RequestBody @Valid ProdutoRequestDTO dto
    ) {
        UpdateProdutoCommand command = ProdutoEntityMapper.toCommand(id, dto);
        Produto produto = updateProdutoUseCase.execute(command);

        return ResponseEntity.status(200).body(ProdutoEntityMapper.toDTO(produto));
    }

    @Operation(summary = "Excluir Produto", description = "Remove um Produto do sistema com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(
            @Parameter(description = "ID do Produto a ser excluído") @PathVariable Integer id
    ) {
        DeleteProdutoCommand command = ProdutoEntityMapper.toCommand(id);
        deleteProdutoUseCase.execute(command);

        return ResponseEntity.status(204).build();
    }

}