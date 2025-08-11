package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoDTO {

    @ApiModelProperty(value = "ID do produto", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome do produto", example = "Pizza Margherita")
    private String nome;

    @ApiModelProperty(value = "Descrição do produto", example = "Pizza com molho de tomate, mussarela e manjericão")
    private String descricao;

    @ApiModelProperty(value = "Preço do produto", example = "29.90")
    private BigDecimal preco;

    @ApiModelProperty(value = "Indica se o produto está ativo", example = "true")
    private boolean ativo;

}
