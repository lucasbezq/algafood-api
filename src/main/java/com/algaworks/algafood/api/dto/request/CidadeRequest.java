package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeRequest {

    @NotBlank
    @ApiModelProperty(example = "Rio de Janeiro", required = true)
    private String nome;

    @Valid
    @NotNull
    private EstadoRefDTO estado;
}
