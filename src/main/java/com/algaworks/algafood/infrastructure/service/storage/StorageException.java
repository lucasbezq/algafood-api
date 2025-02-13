package com.algaworks.algafood.infrastructure.service.storage;

public class StorageException extends RuntimeException {

    private static final long serialVersionUID = 5581048091395656567L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
