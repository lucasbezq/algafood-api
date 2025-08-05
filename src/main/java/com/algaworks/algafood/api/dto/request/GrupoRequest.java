package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GrupoRequest {

    @NotBlank
    @ApiModelProperty(example = "Gerente", required = true)
    private String nome;

}
