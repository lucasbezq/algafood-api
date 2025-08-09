package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaRequest {

    @ApiModelProperty(example = "Brasileira", required = true)
    @NotBlank
    private String nome;

}
