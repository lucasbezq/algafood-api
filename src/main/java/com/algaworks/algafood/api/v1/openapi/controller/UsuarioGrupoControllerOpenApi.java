package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.GrupoDTO;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    @ApiOperation("Lista os grupos associados a um usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de grupos retornada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado")
    })
    public CollectionModel<GrupoDTO> listar(@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId);

    @ApiOperation("Associa um grupo a um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo associado com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado")
    })
    public void associarUsuario(@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId,
                                @ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

    @ApiOperation("Desassocia um grupo de um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo desassociado com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado")
    })
    public void desassociarUsuario(@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId,
                                   @ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);
}

