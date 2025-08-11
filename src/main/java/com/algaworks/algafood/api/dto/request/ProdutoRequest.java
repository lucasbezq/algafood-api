package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequest {

    @NotBlank
    @ApiModelProperty(value = "Nome do produto", example = "Pizza Margherita", required = true)
    private String nome;

    @NotBlank
    @ApiModelProperty(value = "Descrição do produto", example = "Pizza com molho de tomate, mussarela e manjericão", required = true)
    private String descricao;

    @NotNull
    @PositiveOrZero
    @ApiModelProperty(value = "Preço do produto", example = "29.90", required = true)
    private BigDecimal preco;

    @NotNull
    @ApiModelProperty(value = "Indica se o produto está ativo", example = "true", required = true)
    private Boolean ativo;

}
