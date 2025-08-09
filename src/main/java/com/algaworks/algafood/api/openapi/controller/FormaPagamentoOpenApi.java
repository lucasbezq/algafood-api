package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import com.algaworks.algafood.api.dto.request.FormaPagamentoRequest;
import com.algaworks.algafood.api.exceptionHandler.ApiError;
import io.swagger.annotations.*;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoOpenApi {

    @ApiOperation("Lista as formas de pagamento")
    public ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento através do ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento encontrada"),
            @ApiResponse(code = 400, message = "ID da cozinha inválido.", response = ApiError.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada.", response = ApiError.class),
    })
    public ResponseEntity<FormaPagamentoDTO> buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1") Long formaPagamentoId,
                                                    ServletWebRequest request);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de pagamento cadastrada com sucesso."),
            @ApiResponse(code = 400, message = "Dados inválidos para cadastro.", response = ApiError.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada.", response = ApiError.class)
    })
    public FormaPagamentoDTO adicionar(@ApiParam(name = "body", value = "Representação de uma nova forma de pagamento") FormaPagamentoRequest formaPagamentoRequest);

    @ApiOperation("Atualiza uma forma de pagamento através do ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento atualizada com sucesso."),
            @ApiResponse(code = 400, message = "Dados inválidos para atualização.", response = ApiError.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada.", response = ApiError.class)
    })
    public FormaPagamentoDTO atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1") Long formaPagamentoId,
                                       @ApiParam(name = "body", value = "Representação de uma nova forma de pagamento") FormaPagamentoRequest formaPagamentoRequest);

    @ApiOperation("Exclui uma forma de pagamento através do ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento excluída com sucesso."),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada.", response = ApiError.class),
            @ApiResponse(code = 409, message = "Forma de pagamento em uso, não pode ser excluída.", response = ApiError.class)
    })
    void excluir(@ApiParam(value = "ID de uma forma de pagamento", example = "1") Long formaPagamentoId);

}
