package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioSenhaRequest {

    @NotBlank
    @ApiModelProperty(value = "Senha atual do usuário", example = "@Abc123456_*", required = true)
    private String senhaAtual;

    @NotBlank
    @ApiModelProperty(value = "Nova senha do usuário", example = "N0V@Abc123456_*", required = true)
    private String novaSenha;

}
