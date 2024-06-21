package com.algaworks.algafood.api.controller.exceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.util.Constants;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.algaworks.algafood.domain.util.Constants.MSG_ERRO_GENERICA;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var errorType = ErrorType.RECURSO_NAO_ENCONTRADO;
        var detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", e.getRequestURL());

        var error = createProblemBuilder(status, errorType, detail). build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        var errorType = ErrorType.PARAMETRO_INVALIDO;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                e.getName(), e.getValue(), e.getRequiredType().getSimpleName());

        var error = createProblemBuilder(status, errorType, detail).build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        var errorType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

        ApiError error = createProblemBuilder(status, errorType, detail).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException e,
                                                                HttpHeaders headers, HttpStatus status,
                                                                WebRequest request) {
        String path = joinPath(e.getPath());
        var errorType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        var detail =  String.format("A propriedade '%s' recebeu o valor '%s', que é de " +
                "um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'.",
                path, e.getValue(), e.getTargetType().getSimpleName());


        ApiError error = createProblemBuilder(status, errorType, detail).build();

        return handleExceptionInternal(e, error, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException e,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        var path = joinPath(e.getPath());
        ErrorType errorType = ErrorType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        ApiError error = createProblemBuilder(status, errorType, detail). build();

        return handleExceptionInternal(e, error, headers, status, request);
    }


    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var errorType = ErrorType.RECURSO_NAO_ENCONTRADO;
        String detail = e.getMessage();

        ApiError error = createProblemBuilder(status, errorType, detail).userMessage(detail).build();

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

        ApiError error = createProblemBuilder(status, errorType, detail)
                .userMessage(MSG_ERRO_GENERICA)
                .build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(Exception e, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorType = ErrorType.ERRO_DE_SISTEMA;
        var detail = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";

        ApiError error = createProblemBuilder(status, errorType, detail)
                .userMessage(detail).build();

        return handleExceptionInternal(e, error, new HttpHeaders(), status, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        var timestamp = LocalDateTime.now();
        if (body == null) {
            body = ApiError.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .timestamp(timestamp)
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .title(body.toString())
                    .status(status.value())
                    .timestamp(timestamp)
                    .build();
        }

        return super.handleExceptionInternal(e, body, headers, status, request);
    }

    private ApiError.ApiErrorBuilder createProblemBuilder(HttpStatus status, ErrorType type, String detail) {
        return ApiError.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .detail(detail)
                .timestamp(LocalDateTime.now());
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

}
