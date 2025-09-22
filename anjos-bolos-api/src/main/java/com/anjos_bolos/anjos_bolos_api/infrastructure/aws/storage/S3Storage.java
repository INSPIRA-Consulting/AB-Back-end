package com.anjos_bolos.anjos_bolos_api.infrastructure.aws.storage;

import com.anjos_bolos.anjos_bolos_api.core.adapters.StorageGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3Storage implements StorageGateway {

    private final S3Client s3Client;

    @Value("${aws.s3.nome-bucket}")
    private String bucketName;

    public S3Storage(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String upload(byte[] bytes, String key) {
        try {
            PutObjectRequest put = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.putObject(put, RequestBody.fromBytes(bytes));

            return s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(key)).toString();

        } catch (S3Exception ex) {
            throw new ResponseStatusException(500, "Erro ao enviar para S3: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void delete(String key) {
        try {
            DeleteObjectRequest del = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.deleteObject(del);
        } catch (S3Exception ex) {
            ex.printStackTrace();
        }
    }
}
