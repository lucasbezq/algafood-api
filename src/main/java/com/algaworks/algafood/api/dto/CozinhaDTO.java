package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTO {

    @ApiModelProperty(example = "1", value = "ID de uma cozinha")
    private Long id;

    @ApiModelProperty(example = "Brasileira", value = "Nome da cozinha")
    private String nome;

}
