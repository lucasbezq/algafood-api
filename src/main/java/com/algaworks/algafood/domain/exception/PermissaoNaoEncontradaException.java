package com.algaworks.algafood.domain.exception;

import com.algaworks.algafood.domain.util.Constants;

import static com.algaworks.algafood.domain.util.Constants.MSG_PERMISSAO_NAO_ENCONTRADO;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 8727556161882640637L;

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PermissaoNaoEncontradaException(Long id) {
        this(String.format(MSG_PERMISSAO_NAO_ENCONTRADO, id));
    }


}
