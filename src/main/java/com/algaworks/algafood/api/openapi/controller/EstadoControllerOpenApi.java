package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.EstadoDTO;
import com.algaworks.algafood.api.dto.request.EstadoRequest;
import com.algaworks.algafood.api.exceptionHandler.ApiError;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation("Listar estados")
    public List<EstadoDTO> listar();

    @ApiOperation("Buscar estado por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado encontrado"),
            @ApiResponse(code = 404, message = "Estado não encontrado")
    })
    public EstadoDTO buscar(@ApiParam(value = "ID do estado", example = "1") Long estadoId);

    @ApiOperation("Adicionar um novo estado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Estado adicionado com sucesso"),
            @ApiResponse(code = 400, message = "Dados inválidos", response = ApiError.class)
    })
    public EstadoDTO adicionar(@ApiParam(name = "body", value = "Representação de um novo estado") EstadoRequest estadoRequest);

    @ApiOperation("Atualizar um estado existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = ApiError.class),
            @ApiResponse(code = 400, message = "Dados inválidos", response = ApiError.class)
    })
    public EstadoDTO ataualizar(@ApiParam(value = "ID do estado", example = "1") Long estadoId,
                                @ApiParam(name = "body", value = "Representação de um estado atualizado") EstadoRequest estadoRequest);

    @ApiOperation("Remover um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado removido com sucesso"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = ApiError.class),
            @ApiResponse(code = 409, message = "Estado em uso", response = ApiError.class)
    })
    public void remover(@ApiParam(value = "ID do estado", example = "1") Long estadoId);

}
