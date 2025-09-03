package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteDTO extends RepresentationModel<RestauranteDTO> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;

    @ApiModelProperty(example = "12.50")
    private BigDecimal taxaFrete;

    private CozinhaDTO cozinha;

    @ApiModelProperty(example = "true")
    private Boolean ativo;

    @ApiModelProperty(example = "false")
    private Boolean aberto;

    private EnderecoDTO endereco;

}
