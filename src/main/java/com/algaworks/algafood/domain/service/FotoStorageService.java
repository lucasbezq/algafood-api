package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;
import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);
    void remover(String nomeArquivoAntigo);
    FotoRecuperada recuperar(String nomeArquivo);

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (nomeArquivoAntigo != null) {
            this.remover(nomeArquivoAntigo);
        }
    }

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID().toString()
                .concat("_")
                .concat(nomeOriginal);
    }

    @Getter
    @Builder
    class NovaFoto {

        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;
        private Long size;
    }

    @Getter
    @Builder
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean hasUrl() {
            return url != null;
        }

        public boolean hasInputStream() {
            return inputStream != null;
        }
    }

}