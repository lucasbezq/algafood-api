package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Rio de Janeiro")
    private String nome;

    @ApiModelProperty(example = "Rio de Janeiro")
    private String estado;

}
