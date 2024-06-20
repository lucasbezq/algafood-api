package com.algaworks.algafood.api.controller.exceptionHandler;

import lombok.Getter;

@Getter
public enum ErrorType {

    MENSAGEM_INCOMPREENSIVEL("mensagem-incompreensivel", "Mensagem incompreensível."),
    ENTIDADE_NAO_ENCONTRADA("entidade-nao-encontrada", "Entidade não encontrada."),
    ENTIDADE_EM_USO("entidade-em-uso", "Entidade em uso."),
    ERRO_NEGOCIO("erro-negocio", "Violação na regra de negócio."),
    PARAMETRO_INVALIDO("parametro-invalido", "Parâmetro inválido");


    private String title;
    private String uri;

    ErrorType(String path, String title) {
        this.uri = "https://algafood.com.br/" + path;
        this.title = title;
    }
}
