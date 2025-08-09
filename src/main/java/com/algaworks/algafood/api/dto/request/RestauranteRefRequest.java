package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RestauranteRefRequest {

    @ApiModelProperty(value = "ID do restaurante", example = "1", required = true)
    @NotNull
    private Long restauranteId;

}
