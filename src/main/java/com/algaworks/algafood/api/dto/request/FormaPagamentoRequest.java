package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormaPagamentoRequest {

    @ApiModelProperty(value = "Nome da forma de pagamento", example = "Cartão de Crédito", required = true)
    @NotBlank
    private String descricao;

}
