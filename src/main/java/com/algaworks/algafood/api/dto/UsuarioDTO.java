package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Lucas Ezequiel")
    private String nome;

    @ApiModelProperty(example = "lucas@gmail.com")
    private String email;

}
