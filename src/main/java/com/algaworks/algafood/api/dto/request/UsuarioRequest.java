package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioRequest {

    @NotBlank
    @ApiModelProperty(value = "Nome do usuário", example = "Lucas Ezequiel", required = true)
    private String nome;

    @Email
    @NotBlank
    @ApiModelProperty(value = "Email do usuário", example = "lucas@gmail.com", required = true)
    private String email;

    @NotBlank
    @ApiModelProperty(value = "Senha do usuário", example = "@Abc123456_*", required = true)
    private String senha;

}
