package com.algaworks.algafood.domain.exception;

import static com.algaworks.algafood.domain.util.Constants.*;

public class SenhaIncorretaException extends NegocioException {

    private static final long serialVersionUID = -3298543643757750045L;

    public SenhaIncorretaException(String mensagem) {
        super(mensagem);
    }

    public SenhaIncorretaException() {
        this(MSG_SENHA_INCOMPATIVEL);
    }

}
