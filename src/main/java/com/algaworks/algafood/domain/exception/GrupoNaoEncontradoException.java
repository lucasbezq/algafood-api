package com.algaworks.algafood.domain.exception;

import static com.algaworks.algafood.domain.util.Constants.MSG_GRUPO_NAO_ENCONTRADO;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long id) {
        this(String.format(MSG_GRUPO_NAO_ENCONTRADO, id));
    }

}
