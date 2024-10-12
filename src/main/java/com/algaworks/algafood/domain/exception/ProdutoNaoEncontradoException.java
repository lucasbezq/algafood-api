package com.algaworks.algafood.domain.exception;

import static com.algaworks.algafood.domain.util.Constants.*;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = -8103382912844532175L;

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
        this(String.format(MSG_PRODUTO_NAO_ENCONTRADO_NO_RESTAURANTE, produtoId, restauranteId));
    }

}
