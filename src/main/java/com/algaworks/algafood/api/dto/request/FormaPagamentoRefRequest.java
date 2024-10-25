package com.algaworks.algafood.api.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoRefRequest {

    @NotNull
    private Long formaPagamentoId;

}
