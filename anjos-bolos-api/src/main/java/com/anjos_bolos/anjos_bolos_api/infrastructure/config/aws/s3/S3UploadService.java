package com.anjos_bolos.anjos_bolos_api.infrastructure.config.aws.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3UploadService {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3UploadService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(String nomeArquivo, byte[] conteudoArquivo) {
        if (conteudoArquivo == null || conteudoArquivo.length == 0) {
            throw new ResponseStatusException(400,
                    "O arquivo não pode ser vazio", null);
        }

        String s3Key = nomeArquivo ;

        try {
            // Cria a requisição para enviar o objeto
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            // Envia o array de bytes diretamente usando o RequestBody.fromBytes
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromBytes(conteudoArquivo));

            // Retorna o URL do objeto recém-criado (opcional)
            String fileUrl = s3Client.utilities().getUrl(
                    builder -> builder.bucket(bucketName).key(s3Key)).toString();
            return fileUrl;

        } catch (S3Exception exception) {
            exception.printStackTrace();
            // Lidar com possíveis erros da AWS SDK
            throw new ResponseStatusException(500,
                    "Erro de envio p/ o S3: "
                            + exception.getMessage(), exception);
        }
    }

}