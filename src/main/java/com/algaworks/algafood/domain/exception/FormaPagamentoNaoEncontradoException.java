package com.algaworks.algafood.domain.exception;

import static com.algaworks.algafood.domain.util.Constants.MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FormaPagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradoException(Long id) {
        this(String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADA, id));
    }

}
