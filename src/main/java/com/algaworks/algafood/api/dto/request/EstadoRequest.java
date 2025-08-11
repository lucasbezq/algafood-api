package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoRequest {

    @ApiModelProperty(example = "Rio de Janeiro", required = true)
    @NotBlank
    private String nome;

}
