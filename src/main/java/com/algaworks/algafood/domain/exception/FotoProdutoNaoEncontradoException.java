package com.algaworks.algafood.domain.exception;

import static com.algaworks.algafood.domain.util.Constants.MSG_FOTO_PRODUTO_NAO_ENCONTRADO;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 623856392219456093L;

    public FotoProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
        this(String.format(MSG_FOTO_PRODUTO_NAO_ENCONTRADO, produtoId, restauranteId));
    }

}
