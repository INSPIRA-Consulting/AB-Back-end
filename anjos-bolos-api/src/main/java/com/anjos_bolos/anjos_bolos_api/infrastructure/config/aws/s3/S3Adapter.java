package com.anjos_bolos.anjos_bolos_api.infrastructure.config.aws.s3;

import com.anjos_bolos.anjos_bolos_api.infrastructure.persistence.jpa.dto.feriados.FeriadosResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Adapter {

    private final S3Client s3Client;

    @Value("${aws.s3.media-bucket-name}")
    private String mediaBucketName;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Adapter(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String fileName, byte[] fileContent) {
        if (fileContent == null || fileContent.length == 0) {
            throw new ResponseStatusException(400,
                    "O arquivo não pode ser vazio", null);
        }

        try {
            // Cria a requisição para enviar o objeto
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(mediaBucketName)
                    .key(fileName)
                    .build();

            // Envia o array de bytes diretamente usando o RequestBody.fromBytes
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(fileContent));

            // Retorna o URL do objeto recém-criado (opcional)
            return s3Client.utilities().getUrl(
                    builder -> builder.bucket(mediaBucketName).key(fileName)).toString();

        } catch (S3Exception exception) {
            exception.printStackTrace();
            // Lidar com possíveis erros da AWS SDK
            throw new ResponseStatusException(500,
                    "Erro de envio p/ o S3: "
                            + exception.getMessage(), exception);
        }
    }

    public List<FeriadosResponseDTO> listFeriadosNacionais(Integer ano) {
        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .prefix("feriados/nacionais/%d.json".formatted(ano))
                    .build();

            ListObjectsV2Response response = s3Client.listObjectsV2(request);

            List<FeriadosResponseDTO> feriados = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            for (S3Object s3Object : response.contents()) {
                String key = s3Object.key();

                if (key.endsWith("/")) continue;

                GetObjectRequest getRequest = GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();

                byte[] bytes = s3Client.getObject(getRequest, ResponseTransformer.toBytes()).asByteArray();
                String json = new String(bytes, StandardCharsets.UTF_8).trim();

                if (json.isEmpty()) continue;

                if (json.startsWith("[")) {
                    List<FeriadosResponseDTO> partial = mapper.readValue(json, new TypeReference<>() {});
                    feriados.addAll(partial);

                    System.out.println("Added " + partial.size() + " feriados from array.");
                }
                else {
                    FeriadosResponseDTO dto = mapper.readValue(json, FeriadosResponseDTO.class);
                    feriados.add(dto);

                    System.out.println("Added 1 feriado from single object.");
                }
            }

            return feriados;
        } catch (S3Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao buscar feriados no S3: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao ao buscar o conteúdo dos feriados: " + e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao mapear o conteúdo dos feriados: " + e.getMessage(), e);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao processar o conteúdo dos feriados: " + e.getMessage(), e);
        }

    }

    public List<FeriadosResponseDTO> listProximosFeriados(Integer quantidade) {
        List<FeriadosResponseDTO> feriados = new ArrayList<>();
        feriados.addAll(listFeriadosNacionais(LocalDate.now().getYear()));
        feriados.addAll(listFeriadosNacionais((LocalDate.now().getYear()) + 1));

        return feriados.stream()
                .filter(feriado -> feriado.data().isAfter(LocalDate.now()))
                .sorted((f1, f2) -> f1.data().compareTo(f2.data()))
                .limit(quantidade)
                .toList();
    }

}