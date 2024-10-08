package com.algaworks.algafood.domain.exception;

import static com.algaworks.algafood.domain.util.Constants.MSG_USUARIO_NAO_ENCONTRADO;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(Long id) {
        this(String.format(MSG_USUARIO_NAO_ENCONTRADO, id));
    }

}
