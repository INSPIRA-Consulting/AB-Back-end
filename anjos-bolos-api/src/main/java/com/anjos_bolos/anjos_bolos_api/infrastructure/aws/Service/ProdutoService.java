package com.anjos_bolos.anjos_bolos_api.infrastructure.aws.Service;

import com.anjos_bolos.anjos_bolos_api.core.adapters.StorageGateway;
import com.anjos_bolos.anjos_bolos_api.core.domain.produto.Produto;
import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.text.Normalizer;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final StorageGateway storageGateway;

    public ProdutoService(ProdutoRepository produtoRepository, StorageGateway storageGateway) {
        this.produtoRepository = produtoRepository;
        this.storageGateway = storageGateway;
    }

    public Produto criarProdutoComImagem(String nomeProduto, MultipartFile file) {
        if (nomeProduto == null || nomeProduto.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do produto é obrigatório");
        }

        String fileKey = null;
        String fileUrl = null;

        try {

            if (file != null && !file.isEmpty()) {
                String extensao = getExtension(file.getOriginalFilename());
                if (extensao.isBlank()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arquivo sem extensão válida");
                }

                String slug = slugify(nomeProduto);

                String sufixoUnico = String.valueOf(System.currentTimeMillis());

                fileKey = String.format("%s_%s.%s", slug, sufixoUnico, extensao.toLowerCase());
                fileUrl = storageGateway.upload(file.getBytes(), fileKey);
            }

            Produto produto = new Produto();
            produto.setNome(nomeProduto);
            produto.setImagemUrl(fileUrl); // pode ser null
            Produto salvo = produtoRepository.save(produto);

            return salvo;

        } catch (IOException ioEx) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro lendo arquivo: " + ioEx.getMessage(), ioEx);

        } catch (RuntimeException ex) {

            if (fileKey != null) {
                storageGateway.delete(fileKey);
            }

            if (ex instanceof ResponseStatusException) throw ex;
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar produto: " + ex.getMessage(), ex);
        }
    }

    private String getExtension(String filename) {
        if (filename == null) return "";
        int idx = filename.lastIndexOf('.');
        if (idx == -1) return "";
        return filename.substring(idx + 1);
    }

    private String slugify(String input) {
        if (input == null) return "";
        String semAcento = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        String slug = semAcento
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "_")
                .replaceAll("^_|_$", "");
        return slug;
    }
}
