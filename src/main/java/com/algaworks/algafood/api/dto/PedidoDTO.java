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
public class PedidoDTO extends RepresentationModel<PedidoDTO> {

    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;

    @ApiModelProperty(example = "10.50")
    private BigDecimal subTotal;

    @ApiModelProperty(example = "2.50")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "13.00")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "CRIADO")
    private String status;

    @ApiModelProperty(example = "2024-05-20T15:30:00Z")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(example = "2024-05-20T15:35:00Z")
    private OffsetDateTime dataConfirmacao;

    @ApiModelProperty(example = "2024-05-20T16:00:00Z")
    private OffsetDateTime dataEntrega;

    @ApiModelProperty(example = "2024-05-20T15:40:00Z")
    private OffsetDateTime dataCancelamento;

    private RestauranteResumoDTO restaurante;

    private UsuarioDTO cliente;

    private EnderecoDTO enderecoEntrega;

    private FormaPagamentoDTO formaPagamento;

    private List<ItemPedidoDTO> itens;

}
