package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoDTO {

    @ApiModelProperty(value = "ID da forma de pagamento", example = "1")
    private Long id;

    @ApiModelProperty(value = "Descrição da forma de pagamento", example = "Cartão de Crédito")
    private String descricao;

}
