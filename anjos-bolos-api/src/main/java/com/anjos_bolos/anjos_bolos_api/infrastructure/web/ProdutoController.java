package com.anjos_bolos.anjos_bolos_api.infrastructure.web;

import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.aws.Service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<?> criarProduto(@RequestParam("nome") String nome,
                                          @RequestParam(value = "file", required = false) MultipartFile file) {

        Produto salvo = produtoService.criarProdutoComImagem(nome, file);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId()).toUri();

        return ResponseEntity.created(location).body(salvo);
    }
}
