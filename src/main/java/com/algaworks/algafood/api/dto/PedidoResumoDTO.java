package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Relation(collectionRelation = "pedidos")
public class PedidoResumoDTO extends RepresentationModel<PedidoResumoDTO> {

    @ApiModelProperty(value = "Código do pedido", example = "5c621c9a-ba61-4454-8631-8aabefe58dc2")
    private String codigo;

    @ApiModelProperty(value = "Subtotal do pedido", example = "50.00")
    private BigDecimal subTotal;

    @ApiModelProperty(value = "Taxa de entrega do pedido", example = "5.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(value = "Valor total do pedido", example = "55.00")
    private BigDecimal valorTotal;

    @ApiModelProperty(value = "Status do pedido", example = "ENTREGUE")
    private String status;

    @ApiModelProperty(value = "Data e hora de criação do pedido", example = "2023-10-01T10:15:30Z")
    private OffsetDateTime dataCriacao;

    private RestauranteResumoDTO restaurante;

    private UsuarioDTO cliente;

}
