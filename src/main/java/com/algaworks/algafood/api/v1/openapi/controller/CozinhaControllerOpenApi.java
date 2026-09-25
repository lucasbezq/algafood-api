package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.CozinhaDTO;
import com.algaworks.algafood.api.v1.dto.request.CozinhaRequest;
import com.algaworks.algafood.api.exceptionHandler.ApiError;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas")
    public PagedModel<CozinhaDTO> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha através do ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido.", response = ApiError.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada.", response = ApiError.class)
    })
    public CozinhaDTO buscar(@ApiParam(value = "ID de uma cozinha", example = "1") Long cozinhaId);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada com sucesso."),
            @ApiResponse(code = 400, message = "Dados inválidos para cadastro.", response = ApiError.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada.", response = ApiError.class)
    })
    public CozinhaDTO adicionar(CozinhaRequest cozinhaRequest);

    @ApiOperation("Atualiza uma cozinha através do ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada com sucesso."),
            @ApiResponse(code = 404, message = "Cozinha não encontrada.", response = ApiError.class)
    })
    public CozinhaDTO atualizar(@ApiParam(value = "ID de uma cozinha", example = "1") Long cozinhaId,
                                CozinhaRequest cozinhaRequest);

    @ApiOperation("Exclui uma cozinha através do ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída com sucesso."),
            @ApiResponse(code = 404, message = "Cozinha não encontrada.", response = ApiError.class),
            @ApiResponse(code = 409, message = "Cozinha em uso, não pode ser excluída.", response = ApiError.class)
    })
    public void remover(@ApiParam(value = "ID de uma cozinha", example = "1") Long cozinhaId);
}
