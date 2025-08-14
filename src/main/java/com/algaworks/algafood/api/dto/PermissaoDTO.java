package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoDTO {

    @ApiModelProperty(value = "ID da permissão", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome da permissão", example = "CONSULTAR_COZINHAS")
    private String nome;

    @ApiModelProperty(value = "Descrição da permissão", example = "Permite consultar cozinhas")
    private String descricao;

}
