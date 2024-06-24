package com.algaworks.algafood.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ErrorType {

    MENSAGEM_INCOMPREENSIVEL("mensagem-incompreensivel", "Mensagem incompreensível."),
    RECURSO_NAO_ENCONTRADO("recurso-nao-encontrada", "Recurso não encontrada."),
    ENTIDADE_EM_USO("entidade-em-uso", "Entidade em uso."),
    ERRO_NEGOCIO("erro-negocio", "Violação na regra de negócio."),
    PARAMETRO_INVALIDO("parametro-invalido", "Parâmetro inválido"),
    ERRO_DE_SISTEMA("erro-de-sistema", "Erro de sistema"),
    DADOS_INVALIDOS("dados-invalidos", "Dados inválidos");

    private String title;
    private String uri;

    ErrorType(String path, String title) {
        this.uri = "https://algafood.com.br/" + path;
        this.title = title;
    }
}
