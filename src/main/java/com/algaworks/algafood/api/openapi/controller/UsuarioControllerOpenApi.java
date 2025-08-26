package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.api.dto.request.UsuarioAtualizacaoRequest;
import com.algaworks.algafood.api.dto.request.UsuarioRequest;
import com.algaworks.algafood.api.dto.request.UsuarioSenhaRequest;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Listar usuários")
    public CollectionModel<UsuarioDTO> listar();

    @ApiOperation("Buscar usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Usuário encontrado"),
        @ApiResponse(code = 404, message = "Usuário não encontrado")
    })
    public UsuarioDTO buscar(@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId);

    @ApiOperation("Adicionar usuário")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Usuário adicionado com sucesso"),
        @ApiResponse(code = 400, message = "Dados inválidos")
    })
    public UsuarioDTO adicionar(@ApiParam(name = "body", value = "Representação de um novo usuário", required = true) UsuarioRequest usuarioRequest);

    @ApiOperation("Atualizar usuário")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Usuário atualizado com sucesso"),
        @ApiResponse(code = 404, message = "Usuário não encontrado"),
        @ApiResponse(code = 400, message = "Dados inválidos")
    })
    public UsuarioDTO atualizar(@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId,
                                @ApiParam(name = "body", value = "Representação de um novo usuário", required = true) UsuarioAtualizacaoRequest usuarioAtualizacaoRequest);

    @ApiOperation("Atualizar senha do usuário")
    @ApiResponses({
        @ApiResponse(code = 204, message = "Senha atualizada com sucesso"),
        @ApiResponse(code = 404, message = "Usuário não encontrado"),
        @ApiResponse(code = 400, message = "Dados inválidos"),
        @ApiResponse(code = 409, message = "Erro de negócio, como senha atual incorreta")
    })
    public void atualizarSenha(@ApiParam(value = "ID de um usuário", example = "1", required = true) Long usuarioId,
                               @ApiParam(name = "body", value = "Representação de um novo usuário", required = true) UsuarioSenhaRequest usuarioSenhaRequest);

}
