package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoDTO {

    @ApiModelProperty(example = "1")
    private Long produtoId;

    @ApiModelProperty(example = "Pizza de Calabresa")
    private String produtoNome;

    @ApiModelProperty(example = "2")
    private Integer quantidade;

    @ApiModelProperty(example = "25.00")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "50.00")
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "Sem cebola")
    private String observacao;

}
