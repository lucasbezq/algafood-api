package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${algafood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;

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

    private Path getArquivoPath(String nomeArquivo) {
        return diretorioFotos.resolve(nomeArquivo);
    }

}
