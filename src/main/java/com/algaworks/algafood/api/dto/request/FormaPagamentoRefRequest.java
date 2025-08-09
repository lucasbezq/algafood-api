package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoRefRequest {

    @ApiModelProperty(value = "ID da forma de pagamento", example = "1", required = true)
    @NotNull
    private Long formaPagamentoId;

}
