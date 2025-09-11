package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.UsuarioDTO;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

    @ApiOperation("Lista os usuários responsáveis associados a um restaurante")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Lista de usuários responsáveis retornada com sucesso.")
    })
    public CollectionModel<UsuarioDTO> listar(@ApiParam(value = "ID de um restaurante", example = "1") Long restauranteId);

    @ApiOperation("Associa um usuário responsável a um restaurante")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Usuário responsável associado com sucesso."),
        @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado.")
    })
    public ResponseEntity<Void> associar(@ApiParam(value = "ID de um restaurante", example = "1") Long restauranteId,
                                   @ApiParam(value = "ID de um usuário", example = "1")Long usuarioId);

    @ApiOperation("Desassocia um usuário responsável de um restaurante")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Usuário responsável desassociado com sucesso."),
        @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado.")
    })
    public ResponseEntity<Void> desassociar(@ApiParam(value = "ID de um restaurante", example = "1") Long restauranteId,
                            @ApiParam(value = "ID de um usuário", example = "1")Long usuarioId);


}
