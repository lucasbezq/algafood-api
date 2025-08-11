package com.algaworks.algafood.api.openapi.controller;

import io.swagger.annotations.*;

@Api(tags = "Pedidos")
public interface GerenciadorDeStatusPedidoControllerOpenApi {

    @ApiOperation("Confirmação de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido confirmado com sucesso."),
            @ApiResponse(code = 400, message = "Código do pedido inválido."),
            @ApiResponse(code = 404, message = "Pedido não encontrado.")
    })
    public void confirmar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String codigoPedido);

    @ApiOperation("Entrega de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido entregue com sucesso."),
            @ApiResponse(code = 400, message = "Código do pedido inválido."),
            @ApiResponse(code = 404, message = "Pedido não encontrado.")
    })
    public void entregar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String codigoPedido);

    @ApiOperation("Cancelamento de pedido")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pedido cancelado com sucesso."),
            @ApiResponse(code = 400, message = "Código do pedido inválido."),
            @ApiResponse(code = 404, message = "Pedido não encontrado.")
    })
    public void cancelar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String codigoPedido);

}
