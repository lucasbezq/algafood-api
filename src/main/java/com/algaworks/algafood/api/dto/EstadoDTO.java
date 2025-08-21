package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoDTO extends RepresentationModel<EstadoDTO> {

    @NotNull
    @ApiModelProperty(example = "1")
    private Long id;

    @NotBlank
    @ApiModelProperty(example = "Rio de Janeiro")
    private String nome;

}
