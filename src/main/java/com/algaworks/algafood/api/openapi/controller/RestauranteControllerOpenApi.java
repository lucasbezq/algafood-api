package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.RestauranteDTO;
import com.algaworks.algafood.api.dto.RestauranteResumoDTO;
import com.algaworks.algafood.api.dto.request.RestauranteRequest;
import com.algaworks.algafood.api.exceptionHandler.ApiError;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

    @ApiOperation(value = "Lista todos os restaurantes")
    public CollectionModel<RestauranteResumoDTO> listar();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido.", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado.", response = ApiError.class)
    })
    public RestauranteDTO buscar(@ApiParam(value = "ID do restaurante", example = "1") Long restauranteId);

    @ApiOperation("Cadastra um novo restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurante cadastrado com sucesso."),
            @ApiResponse(code = 400, message = "Dados inválidos para o cadastro do restaurante.", response = ApiError.class)
    })
    public RestauranteDTO adicionar(@ApiParam(name = "body", value = "Representação de um novo restaurante") RestauranteRequest restauranteRequest);

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurante atualizado com sucesso."),
            @ApiResponse(code = 404, message = "Restaurante não encontrado.", response = ApiError.class),
            @ApiResponse(code = 400, message = "Dados inválidos para atualização do restaurante.", response = ApiError.class)
    })
    public RestauranteDTO atualizar(@ApiParam(value = "ID do restaurante", example = "1") Long restauranteId,
                                    @ApiParam(name = "body", value = "Representação de um restaurante atualizado") RestauranteRequest restauranteRequest);

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante ativado com sucesso."),
            @ApiResponse(code = 404, message = "Restaurante não encontrado.", response = ApiError.class),
    })
    public void ativar(@ApiParam(value = "ID de um restaurante", example = "1") Long restauranteId);

    @ApiOperation("Ativa uma lista de restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes ativados com sucesso."),
            @ApiResponse(code = 404, message = "Um ou mais restaurantes não encontrados.", response = ApiError.class),
    })
    public void ativarRestaurantes(@ApiParam(name = "body", value = "Lista de IDs de restaurantes") List<Long> restauranteIds);

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante inativado com sucesso."),
            @ApiResponse(code = 404, message = "Restaurante não encontrado.", response = ApiError.class)
    })
    public void inativar(@ApiParam(value = "ID de um restaurante", example = "1") Long restauranteId);

    @ApiOperation("Inativa uma lista de restaurantes")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurantes inativados com sucesso."),
            @ApiResponse(code = 404, message = "Um ou mais restaurantes não encontrados.", response = ApiError.class)
    })
    public void iativarRestaurantes(@ApiParam(name = "body", value = "Lista de IDs de restaurantes") List<Long> restauranteIds);

    @ApiOperation("Abrir um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante aberto com sucesso."),
            @ApiResponse(code = 404, message = "Restaurante não encontrado.", response = ApiError.class)
    })
    public ResponseEntity<Void> abrir(@ApiParam(value = "ID de um restaurante", example = "1") Long restauranteId);


    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurante fechado com sucesso."),
            @ApiResponse(code = 404, message = "Restaurante não encontrado.", response = ApiError.class)
    })
    public ResponseEntity<Void> fechar(@ApiParam(value = "ID de um restaurante", example = "1") Long restauranteId);
}
