package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EnderecoDTO {

    @ApiModelProperty(example = "38400-000")
    private String cep;

    @ApiModelProperty(example = "Rua Floriano Peixoto")
    private String logradouro;

    @ApiModelProperty(example = "1000")
    private String numero;

    @ApiModelProperty(example = "Apto 101")
    private String complemento;

    @ApiModelProperty(example = "Centro")
    private String bairro;

    private CidadeResumoDTO cidade;

}
