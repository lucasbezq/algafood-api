package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.PermissaoDTO;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

    @ApiOperation(value = "Listar permissões de um grupo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Permissões listadas com sucesso"),
        @ApiResponse(code = 404, message = "Grupo não encontrado")
    })
    public List<PermissaoDTO> listar(@ApiParam(value = "ID de um grupo", example = "1") Long grupoId);

    @ApiOperation("Associar permissão a um grupo")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Permissão associada com sucesso"),
        @ApiResponse(code = 404, message = "Grupo ou permissão não encontrado")
    })
    public void associarPermissao(@ApiParam(value = "ID de um grupo", example = "1")Long grupoId,
                                  @ApiParam(value = "ID de uma permissão", example = "1")Long permissaoId);

    @ApiOperation("Desassociar permissão a um grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Permissão associada com sucesso"),
            @ApiResponse(code = 404, message = "Grupo ou permissão não encontrado")
    })
    public void desassociarPermissao(@ApiParam(value = "ID de um grupo", example = "1")Long grupoId,
                                     @ApiParam(value = "ID de uma permissão", example = "1")Long permissaoId);

}
