package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.FormaPagamentoDTO;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @ApiOperation("Lista as formas de pagamento associadas a um restaurante")
    public CollectionModel<FormaPagamentoDTO> listar(@ApiParam(value = "ID de um restaurante", example = "1") Long restauranteId);

    @ApiOperation("Desassocia uma forma de pagamento de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento desassociada com sucesso."),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrada.")
    })
    public ResponseEntity<Void> desassociar(@ApiParam(value = "ID de um restaurante", example = "1") Long restauranteId,
                                      @ApiParam(value = "ID de uma forma de pagamento", example = "1")Long formaPagamentoId);

    @ApiOperation("Associa uma forma de pagamento a um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento associada com sucesso."),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrada.")
    })
    public ResponseEntity<Void> associar(@ApiParam(value = "ID de um restaurante", example = "1") Long restauranteId,
                         @ApiParam(value = "ID de uma forma de pagamento", example = "1")Long formaPagamentoId);

}
