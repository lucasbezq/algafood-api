package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("Pageable")
public class PageableModelOpenApi {

    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
    private String page;

    @ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
    private String size;

    @ApiModelProperty(example = "nome,asc", value = "Nome da propriedade para ordenação dos resultados")
    private List<String> sort;

}
