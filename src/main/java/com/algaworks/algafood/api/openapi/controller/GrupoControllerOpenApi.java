package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.dto.request.GrupoRequest;
import com.algaworks.algafood.api.exceptionHandler.ApiError;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    public List<GrupoDTO> listar();

    @ApiOperation("Busca uma grupo através do ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da grupo inválido.", response = ApiError.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado.", response = ApiError.class)
    })
    public GrupoDTO buscar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);

    @ApiOperation("Cadastra um novo grupo")
    public GrupoDTO adicionar(@ApiParam(name = "body", value = "Representação de um nova grupo") GrupoRequest grupoRequest);

    @ApiOperation("Atualiza um grupo através do ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado."),
            @ApiResponse(code = 404, message = "Grupo não encontrado.", response = ApiError.class)
    })
    public GrupoDTO atualizar(@ApiParam(value = "ID de uma grupo", example = "1") Long grupoId,
                              @ApiParam(name = "body", value = "Representação de uma nova grupo com os novos dados") GrupoRequest grupoRequest);

    @ApiOperation("Exclui um grupo através do ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo atualizado."),
            @ApiResponse(code = 404, message = "Grupo não encontrado.", response = ApiError.class)
    })
    public void excluir(@ApiParam(value = "ID de uma grupo", example = "1") Long grupoId);

}
