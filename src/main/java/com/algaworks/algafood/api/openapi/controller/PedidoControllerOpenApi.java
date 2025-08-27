package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.PedidoDTO;
import com.algaworks.algafood.api.dto.PedidoResumoDTO;
import com.algaworks.algafood.api.dto.request.PedidoRequest;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

    @ApiOperation("Lista os pedidos")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos",
                    value = "Nomes dos campos para filtrar na resposta, separados por vírgula",
                    paramType = "query", dataType = "string")
    })
    public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);

    @ApiOperation("Busca um pedido através do código")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campos",
                    value = "Nomes dos campos para filtrar na resposta, separados por vírgula",
                    paramType = "query", dataType = "string")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "Código do pedido inválido."),
            @ApiResponse(code = 404, message = "Pedido não encontrado.")
    })
    public PedidoDTO buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String codigoPedido);

    @ApiOperation("Cadastra um pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido cadastrado com sucesso."),
            @ApiResponse(code = 400, message = "Dados inválidos para cadastro."),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrada.")
    })
    public PedidoDTO adicionar(@ApiParam(name = "body", value = "Representação de uma nova cidade") PedidoRequest pedidoRequest);

}
