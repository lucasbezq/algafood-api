package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class CidadeResumoDTO extends RepresentationModel<CidadeResumoDTO> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Rio de Janeiro")
    private String nome;

    @ApiModelProperty(example = "Rio de Janeiro")
    private String estado;

}
