package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoRequest {

    @ApiModelProperty(value = "CEP do endereço", example = "38400-000", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(value = "Nome da rua, avenida, etc.", example = "Avenida Brasil", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(value = "Número do endereço", example = "1000", required = true)
    @NotBlank
    private String numero;

    @ApiModelProperty(value = "Complemento do endereço", example = "Apto 101")
    private String complemento;

    @ApiModelProperty(value = "Bairro do endereço", example = "Centro", required = true)
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeRefRequest cidade;

}
