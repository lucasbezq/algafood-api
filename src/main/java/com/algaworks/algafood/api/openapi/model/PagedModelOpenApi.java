package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedModelOpenApi<T> {

    private List<T> content;

    @ApiModelProperty(example = "10", value = "Quantidade total de registros retornados na consulta")
    private Long size;

    @ApiModelProperty(example = "50", value = "Quantidade total de registros disponíveis")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Quantidade total de páginas")
    private Integer totalPages;

    @ApiModelProperty(example = "0", value = "Número da página (começa em 0)")
    private Integer number;
}
