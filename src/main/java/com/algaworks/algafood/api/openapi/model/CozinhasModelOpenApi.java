package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.CozinhaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("CozinhasModel")
public class CozinhasModelOpenApi extends PagedModelOpenApi<CozinhaDTO> {
}
