package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            var arquivo = getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivo));
        } catch (Exception e) {
            throw new StorageException("Não foi possivel armazenar o arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivoAntigo) {
        try {
            var arquivoPath = getArquivoPath(nomeArquivoAntigo);
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo.");
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            var arquivoPath = getArquivoPath(nomeArquivo);
            var fotoRecuperada = FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(arquivoPath))
                    .build();
            return fotoRecuperada;
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo.");
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos()
                .resolve(nomeArquivo);
    }

}
