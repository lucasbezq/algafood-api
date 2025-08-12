package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.FotoProdutoDTO;
import com.algaworks.algafood.api.dto.ProdutoDTO;
import com.algaworks.algafood.api.dto.request.FotoProdutoRequest;
import com.algaworks.algafood.api.dto.request.ProdutoRequest;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = "Produtos")
public interface ProdutoControllerOpenApi {

    @ApiOperation("Lista os produtos de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de produtos retornada com sucesso.")
    })
    public List<ProdutoDTO> listar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @ApiOperation(value = "Busca um produto de um restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto encontrado com sucesso."),
            @ApiResponse(code = 404, message = "Produto/Restaurante não encontrado.")
    })
    public ProdutoDTO buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId,
                             @ApiParam(value = "ID de um produto", example = "1", required = true) Long produtoId);

    @ApiOperation("Adiciona um novo produto a um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto adicionado com sucesso."),
            @ApiResponse(code = 404, message = "Produto/Restaurante não encontrado."),
            @ApiResponse(code = 400, message = "Dados inválidos para criação do produto.")
    })
    public ProdutoDTO adicionar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId,
                                @ApiParam(name = "body", value = "Representação de um novo produto", required = true) ProdutoRequest produtoRequest);

    @ApiOperation("Atualiza um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado com sucesso."),
            @ApiResponse(code = 404, message = "Produto/Restaurante não encontrado."),
            @ApiResponse(code = 400, message = "Dados inválidos para atualização do produto.")
    })
    public ProdutoDTO atualizar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId,
                                @ApiParam(value = "ID de um produto", example = "1", required = true) Long produtoId,
                                @ApiParam(name = "body", value = "Representação de um produto atualizado", required = true) ProdutoRequest produtoRequest);

    @ApiOperation("Atualiza a foto de um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto do produto atualizada com sucesso."),
            @ApiResponse(code = 404, message = "Produto/Restaurante não encontrado."),
            @ApiResponse(code = 400, message = "Dados inválidos para atualização da foto do produto.")
    })
    public FotoProdutoDTO atualizarFoto(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId,
                                        @ApiParam(value = "ID de um produto", example = "1", required = true) Long produtoId,
                                        @ApiParam(name = "body", value = "Representação de uma foto de produto", required = true) FotoProdutoRequest request,
                                        @ApiParam(value = "Arquivo da foto do produto (máximo 500KB, tipos permitidos: JPEG e PNG)", required = true) MultipartFile arquivo) throws IOException;

    @ApiOperation(value = "Busca a foto de um produto de um restaurante", produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto do produto encontrada com sucesso."),
            @ApiResponse(code = 404, message = "Produto/Restaurante não encontrado.")
    })
    public FotoProdutoDTO buscarFoto(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId,
                                     @ApiParam(value = "ID de um produto", example = "1", required = true) Long produtoId);

    @ApiOperation(value = "Serve a foto de um produto de um restaurante", hidden = true)
    public ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;

    @ApiOperation("Remove a foto de um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Foto do produto removida com sucesso."),
            @ApiResponse(code = 404, message = "Produto/Restaurante não encontrado.")
    })
    public void removerFoto(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId,
                            @ApiParam(value = "ID de um produto", example = "1", required = true) Long produtoId);

}
