package com.algaworks.algafood.infrastructure.service.email;

public class EmailExceptionException extends RuntimeException {

    private static final long serialVersionUID = 5581048091395656567L;

    public EmailExceptionException(String message) {
        super(message);
    }

    public EmailExceptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
