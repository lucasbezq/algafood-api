package com.algaworks.algafood.infrastructure.service.storage;


import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class S3StorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 s3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getPath(novaFoto.getNomeArquivo());

            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());
            objectMetadata.setContentLength(novaFoto.getSize());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            s3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
        }
    }

    @Override
    public void remover(String nomeArquivoAntigo) {
        try {
            var request = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(),
                    getPath(nomeArquivoAntigo)
            );

            s3.deleteObject(request);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        var path = getPath(nomeArquivo);
        var url = s3.getUrl(storageProperties.getS3().getBucket(), path);

        return FotoRecuperada.builder()
                .url(url.toString())
                .build();
    }

    private String getPath(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDirectory(), nomeArquivo);
    }

}
