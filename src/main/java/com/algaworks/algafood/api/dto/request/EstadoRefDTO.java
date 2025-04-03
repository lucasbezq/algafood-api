package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoRefDTO {

    @NotNull
    @ApiModelProperty(example = "1")
    private Long id;

}
