package com.algaworks.algafood.api.controller.exceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var errorType = ErrorType.ENTIDADE_NAO_ENCONTRADA;
        String detail = e.getMessage();

        ApiError error = createProblemBuilder(status, errorType, detail).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var errorType = ErrorType.ERRO_NEGOCIO;
        String detail = e.getMessage();

        ApiError error = createProblemBuilder(status, errorType, detail).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var errorType = ErrorType.ENTIDADE_EM_USO;
        String detail = e.getMessage();

        ApiError error = createProblemBuilder(status, errorType, detail).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (body == null) {
            body = ApiError.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .title(body.toString())
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(e, body, headers, status, request);
    }

    private ApiError.ApiErrorBuilder createProblemBuilder(HttpStatus status, ErrorType type, String detail) {
        return ApiError.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .detail(detail);
    }

}
