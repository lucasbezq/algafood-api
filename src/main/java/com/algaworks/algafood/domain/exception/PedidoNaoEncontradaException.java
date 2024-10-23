package com.algaworks.algafood.domain.exception;

import static com.algaworks.algafood.domain.util.Constants.MSG_PEDIDO_NAO_ENCONTRADO;

public class PedidoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 8727556161882640637L;

    public PedidoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradaException(Long id) {
        this(String.format(MSG_PEDIDO_NAO_ENCONTRADO, id));
    }


}
