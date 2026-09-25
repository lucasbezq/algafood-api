package com.algaworks.algafood.api.v1.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Relation(collectionRelation = "permissoes")
public class PermissaoDTO {

    @ApiModelProperty(value = "ID da permissão", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome da permissão", example = "CONSULTAR_COZINHAS")
    private String nome;

    @ApiModelProperty(value = "Descrição da permissão", example = "Permite consultar cozinhas")
    private String descricao;

}
