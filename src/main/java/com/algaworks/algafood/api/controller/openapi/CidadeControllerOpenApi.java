package com.algaworks.algafood.api.controller.openapi;

import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.request.CidadeRequest;
import com.algaworks.algafood.api.exceptionHandler.ApiError;

import io.swagger.annotations.*;


import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    public List<CidadeDTO> listar();

    @ApiOperation("Busca uma cidade através do ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido.", response = ApiError.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada.", response = ApiError.class)
    })
    public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1") Long cidadeId);

    @ApiOperation("Cadastra uma cidade")
    public CidadeDTO adicionar(@ApiParam(name = "body", value = "Representação de uma nova cidade")
                               CidadeRequest cidadeRequest);

    @ApiOperation("Atualiza uma cidade através do ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada."),
            @ApiResponse(code = 404, message = "Cidade não encontrada.", response = ApiError.class)
    })
    public CidadeDTO atualizar(@ApiParam(value = "ID de uma cidade", example = "1") Long cidadeId,
                               @ApiParam(name = "body", value = "Representação de uma nova cidade com os novos dados")
                               CidadeRequest cidadeRequest);

    @ApiOperation("Exclui uma cidade através do ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída."),
            @ApiResponse(code = 404, message = "Cidade não encontrada.", response = ApiError.class)
    })
    public void remover(@ApiParam(value = "ID de uma cidade", example = "1") Long cidadeId);
}
