package com.algaworks.algafood.infrastructure.service.report;

public class ReportException extends RuntimeException {

    private static final long serialVersionUID = 3829113417464531607L;

    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
